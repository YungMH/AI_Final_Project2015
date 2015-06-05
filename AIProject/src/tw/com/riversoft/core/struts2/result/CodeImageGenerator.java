package tw.com.riversoft.core.struts2.result;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;


public class CodeImageGenerator extends StrutsResultSupport {

protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
    
    HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);
    HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(HTTP_REQUEST);
    
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    // 建立Image物件instance
    int width = 60, height = 20;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    //Graphics g = image.getGraphics();
    // 建立亂數產生種子
    Random random = new Random();
    // 設定背景色
    g.setColor(getRandColor(200, 250));
    g.fillRect(0, 0, width, height);
    // 設定字體
    g.setFont(new Font("Arial", Font.PLAIN, 18)); //Times New Roman
    // 畫邊框
    // g.setColor(new Color());
    // g.drawRect(0,0,width-1,height-1);

    // 隨機產生155條干擾線，使圖像中的認證碼不易被解析出來
    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 155; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int xl = random.nextInt(12);
      int yl = random.nextInt(12);
      g.drawLine(x, y, x + xl, y + yl);
    }
    // 取隨機產生的認證碼(4位數字)
    String sRand = "";
    int x = random.nextInt(10); 
    int y = random.nextInt(26); 
    double[] rotates = new double[] {-0.25, 0, 0.25, 0};
    for (int i = 0; i < 4; i++) {
      String rand = String.valueOf(random.nextInt(10));
      sRand += rand;
      g.rotate(rotates[i], x, y);//
      // 將認證碼顯示到圖像中
      g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))); // 調用函數出來的顏色相同，可能是因為種子太接近，所以只能直接生成
      g.drawString(rand, 13 * i + 6, 16);
    }
    // 將認證碼存入SESSION
    ActionContext.getContext().getSession().put("CODE_IMAGE", sRand);
//    HttpSession session = request.getSession(true);
//    session.setAttribute(GlobalSessionConstant.CODE_IMAGE, sRand);

    // 圖像生效
    g.dispose();
    // 輸出圖像到頁面
    ImageIO.write(image, "JPEG", response.getOutputStream());
  }

  private Color getRandColor(int fc, int bc) {// 給定範圍獲得隨機顏色
  
    Random random = new Random();
    if (fc > 255)
      fc = 255;
    if (bc > 255)
      bc = 255;
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }
}
