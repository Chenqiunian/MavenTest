package Demo.Controlller;

import Demo.Mapper.UserDao;
import Demo.Entity.Mail;
import Demo.Entity.User;
import Demo.Service.MailService;
import Demo.Tool.DeviceUtil;
import Demo.Tool.GetTime;
import Demo.Tool.IpUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class TestController {
    @Autowired
    private UserDao userDao;
    @Autowired(required = false)
    private MailService mailService;
    @Autowired
    private GetTime timetool;
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private DeviceUtil deviceUtil;
    @Value("${spring.mail.nickname}")
    private String nickname;

    @RequestMapping("/tiaozhuan")
    String tiaozhuan(HttpServletResponse response, HttpServletRequest request, @RequestParam String a,Device device) throws IOException {
        System.out.println("tiaozhuanController...");

        System.out.println(timetool.gettime()+"  ip地址："+ipUtil.getIpAddr(request)+"，设备："+deviceUtil.getdevice(device));

        switch (a){
            case "vue":
               return "vue";

            case "test":
                return "test";
            case "calendar":
                return "calendar";

            default: return "index";
        }
    }

    @RequestMapping("/SendMailYzm")
    void SendMailYzm(HttpServletResponse response,HttpServletRequest request,@RequestParam(value="mailaddress") String MailAddress,@RequestParam(required = false,value = "fujian") MultipartFile[] fujian){
        System.out.println("SendMailYzmController...");

        String msg = "";

        //前端没选附件后台也收到一个空附件，解决办法
        if(fujian[0].getOriginalFilename().equals("")){
            fujian = null;
        }

        Boolean sendstate = false;

        if(request.getSession().getAttribute("sendtime")==null){
            sendstate = true;
        }
        else{
            Date sendtime = (Date)request.getSession().getAttribute("sendtime");
            long pasttime = timetool.pasttime(sendtime);
            if(pasttime>=60){
                sendstate = true;
            }
            else{
                //计算还有多少秒可以重新发送验证码
                msg = Long.toString(60-pasttime);
                //System.out.println("nowtime"+new Date()+",sendtime="+sendtime+",还有"+msg+"秒可以再发送");
            }
        }


        JSONObject result =new JSONObject();

            if (sendstate) {
                //  获取6为随机验证码
                String[] letters = new String[]{
                        "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
                        //"A","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M",
                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
                String stringBuilder = "";
                for (int i = 0; i < 5; i++) {
                    stringBuilder = stringBuilder + letters[(int) Math.floor(Math.random() * letters.length)];
                }

                Mail mail = new Mail();
                mail.setFrom(nickname + '<' + "2809205039@qq.com" + '>');
                mail.setTo(MailAddress);
                mail.setSubject("蓝月亮邀请您喝洗衣液");
//                https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.16pic.com%2F00%2F49%2F88%2F16pic_4988822_b.jpg&refer=http%3A%2F%2Fpic.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648824717&t=a81cc6c62d8e550f33b1ea7ac9e0e104
                //mail.setText("你的洗衣液验证码为"+stringBuilder+"<img src=\"cid:lyl\"/><br><a href=\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.16pic.com%2F00%2F49%2F88%2F16pic_4988822_b.jpg&refer=http%3A%2F%2Fpic.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648824717&t=a81cc6c62d8e550f33b1ea7ac9e0e104\">蓝月亮洗衣液</a>");
//                mail.setText("你的洗衣液验证码为"+stringBuilder+"<br><img src=\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.16pic.com%2F00%2F49%2F88%2F16pic_4988822_b.jpg&refer=http%3A%2F%2Fpic.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648824717&t=a81cc6c62d8e550f33b1ea7ac9e0e104\"/><br><a href=\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.16pic.com%2F00%2F49%2F88%2F16pic_4988822_b.jpg&refer=http%3A%2F%2Fpic.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648824717&t=a81cc6c62d8e550f33b1ea7ac9e0e104\">蓝月亮洗衣液</a>");
                mail.setText("<!DOCTYPE html><html lang=\"en\"><body><table><tr><td colspan=\"3\">尊敬的王圣焗你好！</td></tr><tr><td style=\"width:90px;\">验证码为：</td><td style=\"color: #007AFF;width: 58px\">"+stringBuilder+"</td><td>，有效时间为5分钟</td></tr><tr><td height=\"30px\" colspan=\"3\"></td></tr><tr><td colspan=\"3\">请勿将验证码告诉他人！<br>系统发信，请勿回复！</td></tr><tr><td><img src=\"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.16pic.com%2F00%2F49%2F88%2F16pic_4988822_b.jpg&refer=http%3A%2F%2Fpic.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648824717&t=a81cc6c62d8e550f33b1ea7ac9e0e104\"></td></tr></table></body></html>");
                //附件
                if (fujian != null && fujian.length > 0) {
                    for (MultipartFile file : fujian)
                        //System.out.println(file.getOriginalFilename());
                    mail.setMultipartFiles(fujian);
                }

                for(int i = 0;i<10;i++) {
                    mailService.sendMail(mail);
                }

                if (mail.getStatus().equals("ok")) {
                    msg = "发送成功";
                    request.getSession().setAttribute("sendtime", new Date());
                    request.getSession().setAttribute("yzm", stringBuilder);
                    request.getSession().setAttribute("mailaddress", MailAddress);
                } else msg = "发送失败";
            }

        result.put("msg",msg);

        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("/checkyzm")
    void checkyzm(HttpServletRequest request,HttpServletResponse response,@RequestParam String yzm,@RequestParam String mailaddress){
        System.out.println("checkyzmController...");

        JSONObject result = new JSONObject();
        String msg = "";

        if(request.getSession().getAttribute("yzm")==null){
            msg = "请先发送验证码";
        }
        else{
            if(mailaddress.equals((String)request.getSession().getAttribute("mailaddress"))) {
                if (timetool.pasttime((Date)request.getSession().getAttribute("sendtime"))> 300) {
                    msg = "验证码已过期，请重新发送";
                }
                else {
                    if (yzm.equals((String) request.getSession().getAttribute("yzm"))) {
                        msg = "验证码正确！";
                    }
                    else {
                        msg = "验证码错误！";
                    }
                }
            }
            else
                msg = "请先发送验证码";
        }

        result.put("msg",msg);
        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("/testdb")
    void testdb(HttpServletRequest request,HttpServletResponse response){
        System.out.println("testdbController..");
        List<User> userlist = userDao.selectall();

        String userdetail = "";

        for (User user:userlist){
            userdetail += user.toString()+"\n";
        }
        JSONObject result = new JSONObject();
        result.put("msg",userdetail);
        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
