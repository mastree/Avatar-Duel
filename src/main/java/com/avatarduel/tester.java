import java.io.*;
import java.util.*;
// import java.lang.*;
import com.avatarduel.model.*;
import com.avatarduel.util.*;
import java.net.URISyntaxException;

class tester{
    public static void main(String[] argv) throws Exception{
        // Card temp = CharacterFactory.getInstance().create();
        Card temp = new Land("Fak", Element.AIR, "tes", "ok");
        System.out.println(temp.getName());
        System.out.println("Jalan");
    }
}