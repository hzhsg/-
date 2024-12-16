import java.sql.Timestamp;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Timestamp past=new Timestamp(System.currentTimeMillis());
        Thread.sleep(1000);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        System.out.println(past.getTime());
        System.out.println(now.getTime());
    }
}