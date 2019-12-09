package com.example.order.print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PrintUtil extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    int PAGES = 1;
    private String printStr = null;
    private int printFlag = -1;
    public JFrame mainFrame = new JFrame();
    public JTextArea area = null;
    private JButton print = new JButton();
    private JScrollPane scrollPane;
    private JPanel btnPanel = new JPanel();

    public PrintUtil() {
        Container contentPane = mainFrame.getContentPane();
        mainFrame.setSize(new Dimension(400, 300));
        mainFrame.setTitle("Print   example ");
        area = new JTextArea(30, 30);
        String str = getStr();
        area.setText(str);
        scrollPane = new JScrollPane(area);
        print = new JButton("Print");
        print.addActionListener(this);
        btnPanel.add(print);
        contentPane.add(btnPanel, BorderLayout.SOUTH);
        contentPane.add(area, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.show();
    }

    public String getStr() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎下次光临");
        sb.append("\r\n--------------------------------\r\n");
        sb.append("日期:" + new Date());
        sb.append("\r\n商品名称    数量  单价   ￥小计");
        sb.append("\r\n--------------------------------\r\n");
        return sb.toString();
    }

    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {

        Graphics2D g2 = (Graphics2D) g;
        System.out.print("a");
        g2.setPaint(Color.red); // 设置打印颜色为黑色
        if (page >= PAGES) // 当打印页号大于需要打印的总页数时，打印工作结束
            return Printable.NO_SUCH_PAGE;
        g2.translate(pf.getImageableX(), pf.getImageableY());// 转换坐标，确定打印边界
        drawCurrentPageText(g2, pf, page); // 打印当前页文本
        return Printable.PAGE_EXISTS; // 存在打印页时，继续打印工作
        // return 1 ;
    }

    // 获取当前页的待打印文本内容
    private void drawCurrentPageText(Graphics2D g2, PageFormat pf, int page) {
        String s = getDrawText(printStr)[page];// 获取当前页的待打印文本内容
        FontRenderContext context = g2.getFontRenderContext();// 获取默认字体及相应的尺寸
        Font f = area.getFont();
        String drawText;
        float ascent = 16; // 给定字符点阵
        int k, i = f.getSize(), lines = 0;
        while (s.length() > 0 && lines < 30) // 每页限定在54行以内
        {
            k = s.indexOf('\n'); // 获取每一个回车符的位置
            if (k != -1) // 存在回车符
            {
                lines += 1; // 计算行数
                drawText = s.substring(0, k); // 获取每一行文本
                g2.drawString(drawText, 0, ascent); // 具体打印每一行文本，同时走纸移位
                if (s.substring(k + 1).length() > 0) {
                    s = s.substring(k + 1); // 截取尚未打印的文本
                    ascent += i;
                }
            } else // 不存在回车符
            {
                lines += 1; // 计算行数
                drawText = s; // 获取每一行文本
                g2.drawString(drawText, 0, ascent); // 具体打印每一行文本，同时走纸移位
                s = ""; // 文本已结束
            }
        }
    }

    /* 将打印目标文本按页存放为字符串数组 */
    public String[] getDrawText(String s) {
        String[] drawText = new String[PAGES];// 根据页数初始化数组
        for (int i = 0; i < PAGES; i++)
            drawText[i] = ""; // 数组元素初始化为空字符串

        int k, suffix = 0, lines = 0;
        while (s.length() > 0) {
            if (lines < 30) // 不够一页时
            {
                k = s.indexOf('\n');
                if (k != -1) // 存在回车符
                {
                    lines += 1; // 行数累加
                    // 计算该页的具体文本内容，存放到相应下标的数组元素
                    drawText[suffix] = drawText[suffix] + s.substring(0, k + 1);
                    if (s.substring(k + 1).length() > 0)
                        s = s.substring(k + 1);
                } else {
                    lines += 1; // 行数累加
                    drawText[suffix] = drawText[suffix] + s; // 将文本内容存放到相应的数组元素
                    s = "";
                }
            } else// 已满一页时
            {
                lines = 0; // 行数统计清零
                suffix++; // 数组下标加1
            }
        }
        return drawText;
    }

    // 计算需要打印的总页数
    public int getPagesCount(String curStr) {
        int page = 0;
        int position, count = 0;
        String str = curStr;
        System.out.println("1");
        while (str.length() > 0) // 文本尚未计算完毕
        {
            System.out.println("2");
            position = str.indexOf('\n'); // 计算回车符的位置
            count += 1; // 统计行数
            if (position != -1)
                str = str.substring(position + 1); // 截取尚未计算的文本
            else
                str = ""; // 文本已计算完毕
        }
        if (count > 0)
            page = count / 54 + 1; // 以总行数除以54获取总页数
        System.out.print(page + "page");
        return page; // 返回需打印的总页数
    }

    public void actionPerformed(ActionEvent evt) {
        printText2Action();
    }

    private void printTextAction()
    {
        printStr = area.getText().trim(); //获取需要打印的目标文本
        if (printStr != null && printStr.length() > 0) //当打印内容不为空时
        {
            PAGES = getPagesCount(printStr); //获取打印总页数
            PrinterJob myPrtJob = PrinterJob.getPrinterJob(); //获取默认打印作业
            PageFormat pageFormat = myPrtJob.defaultPage(); //获取默认打印页面格式
            myPrtJob.setPrintable((Printable) this, pageFormat); //设置打印工作
            if (myPrtJob.printDialog()) //显示打印对话框
            {
                try
                {
                    myPrtJob.print(); //进行每一页的具体打印操作
                }
                catch(PrinterException pe)
                {
                    pe.printStackTrace();
                }
            }
        }
        else
        {
            //如果打印内容为空时，提示用户打印将取消
            JOptionPane.showConfirmDialog(null,
                    "Sorry, Printer Job is Empty, Print Cancelled!", "Empty",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
    }
    // 以jdk1.4新版本提供的API实现打印动作按钮监听，并完成具体的打印操作
    private void printText2Action() {
        printFlag = 0; // 打印标志清零
        printStr = area.getText().trim();// 获取需要打印的目标文本

        System.out.println("the   content   are   ::: ");
        System.out.println(printStr);

        if (printStr != null && printStr.length() > 0) //当打印内容不为空时
        {
            PAGES = getPagesCount(printStr); //获取打印总页数
            //指定打印输出格式
            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            //定位默认的打印服务
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            System.out.println("11"+printService+"----------");

            //创建打印作业
            DocPrintJob job = printService.createPrintJob();
            //设置打印属性
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            DocAttributeSet das = new HashDocAttributeSet();
            //指定打印内容
            Doc doc = new SimpleDoc(this, flavor, das);
            //不显示打印对话框，直接进行打印工作
            try {
                job.print(doc, pras); // 进行每一页的具体打印操作
            } catch (PrintException pe) {
                pe.printStackTrace();
            }
        } else {
            JOptionPane
                    .showConfirmDialog(
                            null,
                            "Sorry,   Printer   Job   is   Empty,   Print   Cancelled! ",
                            "Empty ", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE);
        }
    }

    // test


}