package br.com.icontrol.annotations;

public class AnnotationRunner {

	@Criptografado(name="UE",value="UE2")
	public String pass1 = "TESTE1";
	
	@Criptografado(name="UE",value="UE2")
	public String pass2 = "TESTE2";
	
    public void method1() {
        System.out.println("method1");
    }


    public void method4() {
        System.out.println("method4");
    }

    public void method5() {
        System.out.println("method5");
    }


	public String getPass1() {
		return pass1;
	}


	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}


	public String getPass2() {
		return pass2;
	}


	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

} 
