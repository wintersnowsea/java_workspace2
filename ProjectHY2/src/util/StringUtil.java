package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	// �Ѱܹ��� ���ڰ� 1�ڸ� ���̸�, �տ� 0 ���̱�
	// �������� �� �޼��带 ȣ���ϸ�, ó������� ��ȯ�޴´�
	public static String getNumString(int num) {
		String str = null;
		if (num < 10) { // ���ڸ���
			str = "0" + num; // 05
		} else { // ���ڸ���
			str = Integer.toString(num); // int�� String�� ��ȯ, Wrapper����
		}
		return str;
	}

	/*--------------------------------------------------------------------
	 * Ȯ���� �����Ͽ� ��ȯ�ޱ�
	 * -------------------------------------------------------------------*/
	public static String getExtend(String filename) {
		//String filename = "a.a...a..aaaa..a.png";

		int lastIndex = filename.lastIndexOf(".");
		//System.out.println(lastIndex);
		
		return filename.substring(lastIndex+1, filename.length());
	}
	
	/*-------------------------------------------------------------------
	 * ��й�ȣ ��ȣȭ�ϱ�
	 * ------------------------------------------------------------------*/
	//�ڹ��� ���Ȱ� ���õ� ����� �����ϴ� api ���ִ� ��Ű���� java.security�̴�
	public static String getConvertedPassWord(String pass) {
		StringBuffer hexString=null;
		//��ȣȭ ��ü
		try {
			MessageDigest digest=MessageDigest.getInstance("SHA-256");
			byte[] hash=digest.digest(pass.getBytes("UTF-8")); //��ȣȭ����� �;���
			
			//String�� �Һ��̴�!!
			//���� �� ���� ����� �� ����
			//String ��ü�� �ݺ��� Ƚ���� Ŭ ���� ���� �������� ����ؼ��� �ȵȴ�!!
			//�ذ�å : ���氡���� ���ڿ� ��ü�� �����ϴ� StringBuffer�� StringBuilder ���� Ȱ������
			hexString=new StringBuffer(); //String�� �ƴ϶� �Ϲ�Ŭ�����̴� 
			for(int i=0;i<hash.length;i++) {
				String hex=Integer.toHexString(0xff& hash[i]); //�ڸ����� �Űܼ� 16������ �������
				//System.out.println(hash[i]);
				//System.out.println(hex);
				if(hex.length()==1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}
			//System.out.println(hexString.toString());
			//System.out.println(hexString.length());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
	
	/*-------------------------------------------------------------
	 *���ϸ� �����ϱ� 
	 * ------------------------------------------------------------*/
	//���ϸ� ��ȯ�ϱ�
	public static String createFilename(String url) {
		//���ϸ� �����
		long time=System.currentTimeMillis();
		//Ȯ���� ���ϱ�
		String ext=StringUtil.getExtend(url);
		
		return time+"."+ext;
	}

	
	
//	  public static void main(String[] args) {
//		  String result=getConvertedPassWord("minzino");
//		  System.out.println(result.length());
//	  }
	 
}
