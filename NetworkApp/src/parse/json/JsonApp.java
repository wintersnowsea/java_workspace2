package parse.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonApp {
	/*JSON은 프로그래밍 언어가 아니라 단지 문자열에 불과하다*/
	public static void main(String[] args) {
		StringBuffer sb=new StringBuffer();
		
		//json 표기법은 사실 Map이다!!
		sb.append("{");
		sb.append(" \"name\":\"철수\",");
		sb.append("\"age\":29,");
		sb.append("\"hasPet\":true,");
		sb.append("\"pets\":[");
		sb.append("{");
		sb.append("\"name\":\"원조\",");
		sb.append("\"type\":\"고양이\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"마찌\",");
		sb.append("\"type\":\"고양이\"");
		sb.append("}");
		sb.append("]");
		sb.append("}");
		
		System.out.println(sb.toString());
		//위의 문자열을 실제 객체처럼 사용하려면, 문자열을 객체화시키는 과정이(즉 해석) 필요한 데,
		//프로그래밍 분야에서는 이러한 과정을 "파싱한다"라고 표현한다
		JSONParser jsonParser=new JSONParser();
		//파싱을 하고 나면 문자열에 불가했던 데이터를 실제 객체로 변환해준다
		try {
			Object obj=jsonParser.parse(sb.toString());
			JSONObject json=(JSONObject) obj;
			
			System.out.println(json.get("name"));
			System.out.println(json.get("age"));
			System.out.println(json.get("hasPet"));
			JSONArray array=(JSONArray)json.get("pets");
			
			System.out.println("반려동물 수는 "+array.size());
			for(int i=0;i<array.size();i++) {
				JSONObject pet=(JSONObject)array.get(i);
				System.out.println(pet.get("name"));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
}
