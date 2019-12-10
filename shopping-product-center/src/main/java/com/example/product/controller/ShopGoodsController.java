package com.example.product.controller;


import javax.servlet.http.HttpServletResponse;
import com.example.product.dao.*;
import com.example.product.model.*;
import com.hanfu.common.service.FileMangeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.curator.shaded.com.google.common.collect.Lists;
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
    private ShopPriceMapper shopPriceMapper;
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
    @ApiOperation(value = "类目查询",notes = "类目查询")
    @RequestMapping(value = "/selectCategory",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> SELECT() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.selectCategory()));
    }
    @ApiOperation(value = "品牌查询",notes = "品牌查询")
    @RequestMapping(value = "/selectBrand",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectBrand() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.selectBrand()));
    }
    @ApiOperation(value = "仓库查询",notes = "仓库查询")
    @RequestMapping(value = "/selectWarehouse",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectWarehouse() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopWarehouseMapper.selectAll()));
    }
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
    @ApiOperation(value = "商品规格查询", notes = "商品规格查询")
    @RequestMapping(value = "/selectSpec", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectSpec() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopGoodsService.selectGoods()));
    }

    @ApiOperation(value = "修改商品信息", notes = "修改商品信息")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "类目Id", required = true, type = "VARCHAR"),
            @ApiImplicitParam(paramType = "query", name = "brandId", value = "品牌Id", required = true, type = "VARCHAR"),
            @ApiImplicitParam(paramType = "query", name = "WarehouseId", value = "仓库Id", required = true, type = "VARCHAR")
    })
    public ResponseEntity<JSONObject> updateOrder(HfGoods hfGoods,HfCategory hfCategory,HfBrand hfBrand,HfResp hfResp,HfPrice hfPrice) throws JSONException {
        hfGoods.setCreateTime(LocalDateTime.now());
        hfGoods.setModifyTime(LocalDateTime.now());
        hfGoods.setIsDeleted((short) 0);
        shopGoodsMapper.updateByPrimaryKeySelective(hfGoods);

        //类目，给类目Id hfCategory
        hfCategory.setCreateTime(LocalDateTime.now());
        hfCategory.setModifyTime(LocalDateTime.now());
        hfCategory.setIsDeleted((short) 0);
        hfCategory.setId(hfGoods.getId());
        shopCategoryMapper.updateByPrimaryKeySelective(hfCategory);
        //品牌，给品牌id hfBrand
        hfBrand.setCreateTime(LocalDateTime.now());
        hfBrand.setModifyTime(LocalDateTime.now());
        hfBrand.setIsDeleted((short) 0);
        hfBrand.setId(hfGoods.getBrandId());
        shopBrandMapper.updateByPrimaryKeySelective(hfBrand);
        //库存，给goodsid hfResp
        hfResp.setCreateTime(LocalDateTime.now());
        hfResp.setModifyTime(LocalDateTime.now());
        hfResp.setIsDeleted((short) 0);
        Example example = new Example(HfResp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", hfGoods.getRespId());
        shoppingHfRespMapper.updateByExampleSelective(hfResp, example);
        //价格 goodsid hfPrice
        hfPrice.setCreateTime(LocalDateTime.now());
        hfPrice.setModifyTime(LocalDateTime.now());
        hfPrice.setIsDeleted((short) 0);
        Example example1 = new Example(HfPrice.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("id",hfGoods.getPriceId());
        shopPriceMapper.updateByExampleSelective(hfPrice, example1);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(hfGoods.getId()));
    }
}
