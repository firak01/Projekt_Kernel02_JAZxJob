package basic.zKernel.html;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.IKernelCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJavaCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniSolverZZZ;
import basic.zKernel.job.IJobZZZ;
import basic.zKernel.job.JobHtmlProviderZZZ;
import basic.zKernel.net.client.AbstractMainZZZ;
import basic.zKernel.net.client.ConfigHtmlTableHandlerZZZ;
import basic.zKernel.net.client.HtmlTableHandlerApplicationZZZ;
import basic.zKernel.net.client.IApplicationZZZ;
import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

public class HtmlTableHandlerByJobMainZZZ extends AbstractMainZZZ {	
	/**Entry point for the OVPN-Client-Starter.
	 * @return void
	 * @param args 
	 * 
	 * lindhaueradmin; 08.07.2006 08:24:16
	 */
	public static void main(String[] args) {
		try {
			HtmlTableHandlerByJobMainZZZ objClient = new HtmlTableHandlerByJobMainZZZ();		
			objClient.start(args);
		} catch (ExceptionZZZ ez) {			
				ez.printStackTrace();
				System.out.println(ez.getDetailAllLast());			
		}//END Catch
	}//END main()
	
	public HtmlTableHandlerByJobMainZZZ() throws ExceptionZZZ {
		super();
	}
	public HtmlTableHandlerByJobMainZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, saFlagControl);
	}
	
	public boolean start(String[] saArg){
		boolean bReturn = false;
		main:{						
			try {
				//Parameter aus args auslesen
				String[]saFlag = {IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name(),IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(), IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA_MATH.name(),IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name(),IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name(),IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name(), IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name(),IKernelCallIniSolverZZZ.FLAGZ.USECALL.name(), IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA.name()};
				ConfigHtmlTableHandlerZZZ objConfig = new ConfigHtmlTableHandlerZZZ(saArg, saFlag);
				IKernelZZZ objKernel = new KernelZZZ(objConfig, (String) null); //Damit kann man über die Startparameter ein anders konfiguriertes Kernel-Objekt erhalten.
				this.setKernelObject(objKernel);
																
				//DAS BACKEND handlebar machen
				IApplicationZZZ objApplication = new HtmlTableHandlerApplicationZZZ(objKernel, null);
				objApplication.setMainObject(this);
				this.setApplicationObject(objApplication);
				
				//TEST:
				String sUrl = objApplication.getUrlToParse();
				System.out.println("URL to parse ='" + sUrl + "'");
				
				//DAS UI handlebar machen
				//### 1. Voraussetzung: OpenVPN muss auf dem Rechner vorhanden sein. Bzw. die Dateiendung .ovpn ist registriert. 
				//this.objClientTray = new ClientTrayUIZZZ(objKernel, this.objClientMain, (String[]) null);
				
				//### Arbeite mit einem JOB
				IJobZZZ objJobHtml = new JobHtmlProviderZZZ(objApplication);
				boolean bSuccess = objJobHtml.process();
				if(bSuccess) {
					System.out.println("Erfolgreich verarbeitet.");
				}else {
					System.out.println(("NICHT erfolgreich verarbeitet"));
				}
			} catch (ExceptionZZZ ez) {
				if(objKernel!=null){
					LogZZZ objLog = objKernel.getLogObject();
					if(objLog!=null){
						objLog.WriteLineDate(ez.getDetailAllLast());
					}else{
						ez.printStackTrace();
						System.out.println(ez.getDetailAllLast());
					}				
				}else{
					ez.printStackTrace();
					System.out.println(ez.getDetailAllLast());
				}
			}//END Catch
			}//END main:
		System.out.println("finished starting application.");
		return bReturn;			
	}
	
	public boolean start() throws ExceptionZZZ{
		return this.start(null);
	}

	@Override
	public void run() {
		try {
			this.start();
		} catch (ExceptionZZZ e) {
			try {
				this.setFlag("haserror", true);
			} catch (ExceptionZZZ e1) {				
				e1.printStackTrace();
			}
			this.getKernelObject().getLogObject().WriteLineDate(e.getDetailAllLast());
		}
	}
	
}//END class
