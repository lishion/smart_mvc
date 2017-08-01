import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;

import javax.swing.plaf.synth.SynthOptionPaneUI;

/**
 * Created by Lishion on 2017/7/29.
 */
@Bean(BeanType.Model)
public class Main {
    public static void main(String[] args){
        String s = "http://localhost:8080/smart/smart/smart/aaa/smart";

        int last = s.indexOf("/smart");
        System.out.println(last+"/smart".length());
        System.out.println( s.substring(last+"/smart".length(),s.length()) );
        System.out.println(BeanType.isModel(Main.class));
    }
}
