package com.example.product.controller;


import javax.servlet.http.HttpServletResponse;
import com.example.product.dao.*;
import com.example.product.model.*;
import com.hanfu.common.service.FileMangeService;
import com.sun.jdi.Value;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.example.product.service.ShopGoodsService;
import com.shopping.utils.response.handler.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.shopping.utils.response.handler.ResponseEntity.BodyBuilder;
import com.shopping.utils.response.handler.ResponseUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/goods")
@Api
public class ShopGoodsController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopGoodsMapper shopGoodsMapper;
    @Autowired
    private GoodsFileMapper goodsFileMapper;
    @Autowired
    private ShopGoodsPictrueMapper shopGoodsPictrueMapper;
    @Autowired
    private ShoppingHfRespMapper shoppingHfRespMapper;
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;
    @Autowired
    private ShopWarehouseMapper shopWarehouseMapper;
    @Autowired
    private ShopBrandMapper shopBrandMapper;
    @Autowired
    private ShopRespMapper shopRespMapper;
    @Autowired
    ShopPriceMapper shopPriceMapper;
    @Autowired
    ShopGoodsSpecMapper shopGoodsSpec;
    @ApiOperation(value = "获取商品goods", notes = "获取商品")
    @RequestMapping(value = "/selectGoods", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectGoods() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.selectGoods()));
    }
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping(value = "/getImg")
    public ResponseEntity<JSONObject> getImg(MultipartFile fileInfo, FileDesc fileDesc2,HfGoodsPictrue hfGoodsPictrue2) throws JSONException, IOException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<HfGoodsPictrue> pictures = Lists.newArrayList();
        try {
            FileMangeService fileMangeService = new FileMangeService();
            String arr[];
            arr = fileMangeService.uploadFile(fileInfo.getBytes(),String.valueOf(fileDesc2.getUserId()));
            FileDesc fileDesc = new FileDesc();
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setFileName(fileDesc2.getFileName());
            fileDesc.setUserId(fileDesc2.getUserId());
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            goodsFileMapper.insert(fileDesc);
            HfGoodsPictrue hfGoodsPictrue = new HfGoodsPictrue();
            hfGoodsPictrue.setFileId(fileDesc.getId());
            hfGoodsPictrue.setGoodsId(hfGoodsPictrue2.getGoodsId());
            hfGoodsPictrue.setHfName(fileInfo.getName());
            hfGoodsPictrue.setSpecDesc(hfGoodsPictrue2.getSpecDesc());
            hfGoodsPictrue.setCreateTime(LocalDateTime.now());
            hfGoodsPictrue.setModifyTime(LocalDateTime.now());
            hfGoodsPictrue.setIsDeleted((short) 0);
            hfGoodsPictrue.setLastModifier(String.valueOf(fileDesc2.getUserId()));
            shopGoodsPictrueMapper.insert(hfGoodsPictrue);
            pictures.add(hfGoodsPictrue);
        } catch (IOException e) {
            logger.error("add picture failed", e);
        }
        return builder.body(ResponseUtils.getResponseBody(pictures));
    }
    @ApiOperation(value = "商品基本信息添加", notes = "商品基本信息添加")
    @RequestMapping(value = "/insertBasicGoods", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "类目Id", required = true, type = "VARCHAR"),
            @ApiImplicitParam(paramType = "query", name = "brandId", value = "品牌Id", required = true, type = "VARCHAR"),
            @ApiImplicitParam(paramType = "query", name = "WarehouseId", value = "仓库Id", required = true, type = "VARCHAR")
    })
    public ResponseEntity<JSONObject> basicGoods(HfGoods hfGoods, HfResp hfResp) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        int hfGoodsId = shopGoodsService.basicGoods(hfGoods);
        System.out.println(hfGoodsId);

        hfResp.setCreateTime(LocalDateTime.now());
        hfResp.setModifyTime(LocalDateTime.now());
        hfResp.setIsDeleted((short) 0);
        hfResp.setGoogsId(hfGoodsId);
        shoppingHfRespMapper.insert(hfResp);
        return builder.body(ResponseUtils.getResponseBody(hfGoodsId));
    }
    /*----------------------------------类目------------------------------------------------------------*/
    @ApiOperation(value = "类目查询",notes = "类目查询")
    @RequestMapping(value = "/selectCategory",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> SELECT() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.selectCategory()));
    }
    @ApiOperation(value = "类目增加",notes = "类目增加")
    @RequestMapping(value = "/insertCategory",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> insertCategory(HfCategory hfCategory) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfCategory.setCreateTime(LocalDateTime.now());
        hfCategory.setModifyTime(LocalDateTime.now());
        hfCategory.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(shopCategoryMapper.insert(hfCategory)));
    }
    @ApiOperation(value = "类目删除",notes = "类目删除")
    @RequestMapping(value = "/deleteCategory",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "类目Id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> deleteCategory(HfCategory hfCategory) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopCategoryMapper.deleteByPrimaryKey(hfCategory)));
    }
    @ApiOperation(value = "类目修改",notes = "类目修改")
    @RequestMapping(value = "/updateCategory",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "类目Id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> updateCategory(HfCategory hfCategory) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfCategory.setCreateTime(LocalDateTime.now());
        hfCategory.setModifyTime(LocalDateTime.now());
        return builder.body(ResponseUtils.getResponseBody(shopCategoryMapper.updateByPrimaryKeySelective(hfCategory)));
    }
    /*--------------------------------------品牌----------------------------------------------*/
    @ApiOperation(value = "品牌查询",notes = "品牌查询")
    @RequestMapping(value = "/selectBrand",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectBrand() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.selectBrand()));
    }
    @ApiOperation(value = "品牌增加",notes = "品牌增加")
    @RequestMapping(value = "/insertBrand",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> insertBrand(HfBrand hfBrand) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfBrand.setIsDeleted((short) 0);
        hfBrand.setModifyTime(LocalDateTime.now());
        hfBrand.setCreateTime(LocalDateTime.now());
        return builder.body(ResponseUtils.getResponseBody(shopBrandMapper.insert(hfBrand)));
    }
    @ApiOperation(value = "品牌删除",notes = "品牌删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "品牌Id", required = true, type = "VARCHAR"),
    })
    @RequestMapping(value = "/deleteBrand",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> deleteBrand(HfBrand hfBrand) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopBrandMapper.deleteByPrimaryKey(hfBrand)));
    }
    @ApiOperation(value = "品牌修改",notes = "品牌修改")
    @RequestMapping(value = "/updateBrand",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "品牌Id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> updateBrand(HfBrand hfBrand) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfBrand.setIsDeleted((short) 0);
        hfBrand.setModifyTime(LocalDateTime.now());
        hfBrand.setCreateTime(LocalDateTime.now());
        return builder.body(ResponseUtils.getResponseBody(shopBrandMapper.updateByPrimaryKeySelective(hfBrand)));
    }
    /*----------------------------------------------仓库---------------------------------*/
    @ApiOperation(value = "仓库查询",notes = "仓库查询")
    @RequestMapping(value = "/selectWarehouse",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectWarehouse() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopWarehouseMapper.selectAll()));
    }
    @ApiOperation(value = "仓库增加",notes = "仓库增加")
    @RequestMapping(value = "/insertWarehouse",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> insertWarehouse(HfWarehouse hfWarehouse) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfWarehouse.setModifyTime(LocalDateTime.now());
        hfWarehouse.setCreateTime(LocalDateTime.now());
        hfWarehouse.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(shopWarehouseMapper.insert(hfWarehouse)));
    }
    @ApiOperation(value = "仓库删除",notes = "仓库删除")
    @RequestMapping(value = "/deleteWarehouse",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "仓库id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> deleteWarehouse(HfWarehouse hfWarehouse) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopWarehouseMapper.deleteByPrimaryKey(hfWarehouse)));
    }
    @ApiOperation(value = "仓库修改",notes = "仓库修改")
    @RequestMapping(value = "/updateWarehouse",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "仓库id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> updateWarehouse(HfWarehouse hfWarehouse) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfWarehouse.setModifyTime(LocalDateTime.now());
        hfWarehouse.setCreateTime(LocalDateTime.now());
        return builder.body(ResponseUtils.getResponseBody(shopWarehouseMapper.updateByPrimaryKeySelective(hfWarehouse)));
    }
    /*---------------------------规格----------------------------------------------------------------------------*/
    @ApiOperation(value = "规格查询",notes = "规格查询")
    @RequestMapping(value = "/selectSpec",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectSpec(int goodsIdS) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoodsSpec hfGoodsSpec = new HfGoodsSpec();
        hfGoodsSpec.setGoodsId(goodsIdS);
        Example example = new Example(HfGoodsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId",goodsIdS);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsSpec.selectByExample(example)));
    }
    @ApiOperation(value = "规格增加",notes = "规格增加")
    @RequestMapping(value = "/insertSpec",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> insertSpec(HfGoodsSpec hfGoodsSpec) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfGoodsSpec.setGoodsId(hfGoodsSpec.getGoodsId());
        hfGoodsSpec.setCreateTime(LocalDateTime.now());
        hfGoodsSpec.setModifyTime(LocalDateTime.now());
        hfGoodsSpec.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsSpec.insert(hfGoodsSpec)));
    }
    @ApiOperation(value = "规格删除",notes = "规格删除")
    @RequestMapping(value = "/deleteSpec",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> deleteSpec(int goodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Example example = new Example(HfGoodsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId",goodsId);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsSpec.deleteByExample(example)));
    }
    @ApiOperation(value = "规格修改",notes = "规格修改")
    @RequestMapping(value = "/updateSpec",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "规格id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> updateSpec(HfGoodsSpec hfGoodsSpec,int goodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfGoodsSpec.setCreateTime(LocalDateTime.now());
        hfGoodsSpec.setModifyTime(LocalDateTime.now());
        hfGoodsSpec.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsSpec.updateByPrimaryKeySelective(hfGoodsSpec)));
    }
    /*-------------------------------------------我是分割线-------------------------------------------------------------*/
    @ApiOperation(value = "获取图片",notes = "获取图片")
    @RequestMapping(value = "/selectFile",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectFile(@RequestParam(name = "goodsId") Integer goodsId, HttpServletResponse response) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Example example = new Example(HfGoodsPictrue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        List<HfGoodsPictrue> lists = shopGoodsPictrueMapper.selectByExample(example);
        System.out.println(lists.get(0).getFileId());
        for (int i=0;i<lists.size();i++){
            if(!lists.isEmpty()) {
                FileDesc fileDesc = goodsFileMapper.selectByPrimaryKey(lists.get(i).getFileId());
                if (fileDesc == null) {
                    throw new Exception("file not exists");
                }
                FileMangeService fileManageService = new FileMangeService();
                byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
                if(file!=null) {
                    BufferedImage readImg = ImageIO.read(new ByteArrayInputStream(file));
                    ImageIO.write(readImg, "png", response.getOutputStream());
                }
            }
        }
        return builder.body(ResponseUtils.getResponseBody(lists));
    }

    @ApiOperation(value = "删除单张图片", notes = "删除单张图片")
    @RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "物品图片id", required = true, type = "Integer") })
    public void deletePicture(@RequestParam(name = "id") Integer id) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        System.out.println(id);
        HfGoodsPictrue hfGoodsPictrue = shopGoodsPictrueMapper.selectByPrimaryKey(id);
        System.out.println(hfGoodsPictrue);
        if(hfGoodsPictrue!=null) {
            FileDesc fileDesc = goodsFileMapper.selectByPrimaryKey(hfGoodsPictrue.getFileId());
            FileMangeService fileMangeService = new FileMangeService();
            fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
            goodsFileMapper.deleteByPrimaryKey(fileDesc.getId());
            shopGoodsPictrueMapper.deleteByPrimaryKey(id);
            System.out.println(id);
        }
    }
    @ApiOperation(value = "删除商品图片", notes = "删除图片根据物品")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
    public void deleteFile(@RequestParam(name = "goodsId") Integer goodsId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Example example = new Example(HfGoodsPictrue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        List<HfGoodsPictrue> list = shopGoodsPictrueMapper.selectByExample(example);
        for(int i=0;i<list.size();i++) {
            FileDesc fileDesc = goodsFileMapper.selectByPrimaryKey(list.get(i).getFileId());
            FileMangeService fileManageService = new FileMangeService();
            if(fileDesc!=null) {
                fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
            }
            shopGoodsPictrueMapper.deleteByPrimaryKey(list.get(i).getId());
            goodsFileMapper.deleteByPrimaryKey(fileDesc.getId());
        }
    }
    @ApiOperation(value = "修改商品Goods", notes = "修改商品Goods")
    @RequestMapping(value = "/updateGoods", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> updateGoods(AlterGoods alterGoods) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //商品
        HfGoods hfGoods = new HfGoods();
        hfGoods.setId(alterGoods.getGoogsId());
        hfGoods.setCreateTime(LocalDateTime.now());
        hfGoods.setModifyTime(LocalDateTime.now());
        hfGoods.setIsDeleted((short) 0);
        hfGoods.setHfName(alterGoods.getGoodsName());
        hfGoods.setCategoryId(alterGoods.getCategoryId());
        hfGoods.setBrandId(alterGoods.getBrandId());
        hfGoods.setRespId(alterGoods.getRespId());
        hfGoods.setPriceId(alterGoods.getPriceId());
        hfGoods.setGoodsDesc(alterGoods.getGoodsDesc());
        hfGoods.setStoneId(alterGoods.getStoneId());
        shopGoodsMapper.updateByPrimaryKeySelective(hfGoods);
//        //类目
//        HfCategory hfCategory = new HfCategory();
//        hfCategory.setId(alterGoods.getCategoryId());
//        hfCategory.setHfName(alterGoods.getHfCategoryName());
//        hfCategory.setCreateTime(LocalDateTime.now());
//        hfCategory.setModifyTime(LocalDateTime.now());
//        hfCategory.setIsDeleted((short) 0);
//        shopCategoryMapper.updateByPrimaryKeySelective(hfCategory);
//        //品牌
//        HfBrand hfBrand = new HfBrand();
//        hfBrand.setId(alterGoods.getBrandId());
//        hfBrand.setHfName(alterGoods.getBrandName());
//        hfBrand.setBrandType(alterGoods.getBrandType());
//        hfBrand.setHfDesc(alterGoods.getHfDesc());
//        hfBrand.setCreateTime(LocalDateTime.now());
//        hfBrand.setModifyTime(LocalDateTime.now());
//        hfBrand.setIsDeleted((short) 0);
//        shopBrandMapper.updateByPrimaryKeySelective(hfBrand);
        //库存
        HfResp hfResp = new HfResp();
        hfResp.setGoogsId(alterGoods.getGoogsId());
        hfResp.setWarehouseId(alterGoods.getWarehouseId());
        hfResp.setQuantity(alterGoods.getQuantity());
        hfResp.setHfDesc(alterGoods.getRespDesc());
        hfResp.setHfStatus(alterGoods.getHfStatus());
        hfResp.setLastModifier(alterGoods.getLastModifier());
        hfResp.setCreateTime(LocalDateTime.now());
        hfResp.setModifyTime(LocalDateTime.now());
        hfResp.setIsDeleted((short) 0);
        Example example = new Example(HfResp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("googsId", alterGoods.getGoogsId());
        shopRespMapper.updateByExampleSelective(hfResp, example);
        //价格
        HfPrice hfPrice = new HfPrice();
        hfPrice.setId(alterGoods.getPriceId());
        hfPrice.setCreateTime(LocalDateTime.now());
        hfPrice.setModifyTime(LocalDateTime.now());
        hfPrice.setIsDeleted((short) 0);
        hfPrice.setGoogsId(alterGoods.getGoogsId());
        hfPrice.setSellPrice(alterGoods.getSellPrice());
        hfPrice.setHfDesc(alterGoods.getPriceDesc());
        hfPrice.setLastModifier(alterGoods.getLastModifier());

        Example example1 = new Example(HfPrice.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("googsId", alterGoods.getGoogsId());
        shopPriceMapper.updateByExampleSelective(hfPrice, example1);
        //商品规格
        HfGoodsSpec hfGoodsSpec = new HfGoodsSpec();
        hfGoodsSpec.setCreateTime(LocalDateTime.now());
        hfGoodsSpec.setModifyTime(LocalDateTime.now());
        hfGoodsSpec.setIsDeleted((short) 0);
        hfGoodsSpec.setLastModifier(alterGoods.getLastModifier());
        hfGoodsSpec.setHfValue(alterGoods.getHfValue());
        Example example2 = new Example(HfGoodsSpec.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("goodsId",alterGoods.getGoogsId());
        shopGoodsSpec.updateByExampleSelective(hfGoodsSpec,example2);


        return builder.body(ResponseUtils.getResponseBody(alterGoods));
    }
    @ApiOperation(value = "保存商品Goods", notes = "保存商品Goods")
    @RequestMapping(value = "/insertGoods", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> insertGoods(AlterGoods alterGoods) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //商品
        HfGoods hfGoods = new HfGoods();
        hfGoods.setIsDeleted((short) 0);
        hfGoods.setCreateTime(LocalDateTime.now());
        hfGoods.setModifyTime(LocalDateTime.now());
        hfGoods.setHfName(alterGoods.getGoodsName());
        hfGoods.setCategoryId(alterGoods.getCategoryId());
        hfGoods.setBrandId(alterGoods.getBrandId());
        hfGoods.setRespId(alterGoods.getRespId());
        hfGoods.setPriceId(alterGoods.getPriceId());
        hfGoods.setGoodsDesc(alterGoods.getGoodsDesc());
        shopGoodsMapper.insert(hfGoods);
        int GoodsId=hfGoods.getId();
//        //类目
//        HfCategory hfCategory = new HfCategory();
//        hfCategory.setId(alterGoods.getCategoryId());
//        hfCategory.setHfName(alterGoods.getHfCategoryName());
//        hfCategory.setCreateTime(LocalDateTime.now());
//        hfCategory.setModifyTime(LocalDateTime.now());
//        hfCategory.setIsDeleted((short) 0);
//        shopCategoryMapper.insert(hfCategory);
//        //品牌
//        HfBrand hfBrand = new HfBrand();
//        hfBrand.setHfName(alterGoods.getBrandName());
//        hfBrand.setBrandType(alterGoods.getBrandType());
//        hfBrand.setHfDesc(alterGoods.getHfDesc());
//        hfBrand.setCreateTime(LocalDateTime.now());
//        hfBrand.setModifyTime(LocalDateTime.now());
//        hfBrand.setIsDeleted((short) 0);
//        shopBrandMapper.insert(hfBrand);
        //库存
        HfResp hfResp = new HfResp();
        hfResp.setGoogsId(GoodsId);
        hfResp.setLastModifier(alterGoods.getLastModifier());
        hfResp.setWarehouseId(alterGoods.getWarehouseId());
        hfResp.setQuantity(alterGoods.getQuantity());
        hfResp.setHfDesc(alterGoods.getRespDesc());
        hfResp.setHfStatus(alterGoods.getHfStatus());
        hfResp.setIsDeleted((short) 0);
        hfResp.setCreateTime(LocalDateTime.now());
        hfResp.setModifyTime(LocalDateTime.now());
        Example example = new Example(HfResp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("googsId", alterGoods.getGoogsId());
        shopRespMapper.insert(hfResp);
        //价格
        HfPrice hfPrice = new HfPrice();
        hfPrice.setCreateTime(LocalDateTime.now());
        hfPrice.setModifyTime(LocalDateTime.now());
        hfPrice.setIsDeleted((short) 0);
        hfPrice.setHfDesc(alterGoods.getPriceDesc());
        hfPrice.setGoogsId(GoodsId);
        hfPrice.setSellPrice(alterGoods.getSellPrice());
        hfPrice.setLastModifier(alterGoods.getLastModifier());
        shopPriceMapper.insert(hfPrice);
        //商品规格
        HfGoodsSpec hfGoodsSpec = new HfGoodsSpec();
        hfGoodsSpec.setCreateTime(LocalDateTime.now());
        hfGoodsSpec.setModifyTime(LocalDateTime.now());
        hfGoodsSpec.setIsDeleted((short) 0);
        hfGoodsSpec.setLastModifier(alterGoods.getLastModifier());
        hfGoodsSpec.setHfValue(alterGoods.getHfValue());
        hfGoodsSpec.setGoodsId(GoodsId);
        shopGoodsSpec.insert(hfGoodsSpec);
        return builder.body(ResponseUtils.getResponseBody(alterGoods));
    }
    @ApiOperation(value = "获取全部信息商品goods", notes = "获取全部信息商品goods")
    @RequestMapping(value = "/selectGoodsAll", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectGoodsAll() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.SelectGoodsAll()));
    }
    @ApiOperation(value = "删除全部信息商品goods", notes = "删除全部信息商品goods")
    @RequestMapping(value = "/deleteGoodsAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "GoodsId", value = "商品Id", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> deleteGoodsAll(int GoodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Example example = new Example(HfResp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("googsId",GoodsId);
        shopRespMapper.deleteByExample(example);

        Example example1 = new Example(HfPrice.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("googsId",GoodsId);
        shopPriceMapper.deleteByExample(example1);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.deleteGoodsAll(GoodsId)));
    }
    @ApiOperation(value = "批量删除全部信息商品goods", notes = "批量删除全部信息商品goods")
    @RequestMapping(value = "/deleteGoodsBatch", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> deleteGoodsBatch(@RequestParam("BatchGoodsId") List BatchGoodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        for (int i=0;i<BatchGoodsId.size();i++){
            int goodsID=Integer.parseInt(BatchGoodsId.get(i).toString());
            Example example = new Example(HfResp.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("googsId",goodsID);
            shopRespMapper.deleteByExample(example);
            Example example1 = new Example(HfPrice.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("googsId",goodsID);
            shopPriceMapper.deleteByExample(example1);
            shopGoodsService.deleteGoodsAll(goodsID);
            System.out.println(goodsID);
        }
        return builder.body(ResponseUtils.getResponseBody(BatchGoodsId));
    }
}
