package basic.zKernel.html;

import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.IKernelCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJavaCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniZZZ;
import basic.zKernel.job.IJobZZZ;
import basic.zKernel.job.JobHtmlProviderZZZ;
import basic.zKernel.net.client.AbstractMainZZZ;
import basic.zKernel.net.client.ConfigHtmlTableHandlerZZZ;
import basic.zKernel.net.client.HtmlTableHandlerApplicationZZZ;
import basic.zKernel.net.client.IApplicationUserZZZ;
import basic.zKernel.net.client.IApplicationZZZ;
import custom.zKernel.LogZZZ;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;

/**
 *  Merke: Diese Klasse implementiert als Main schon IApplicationUserZZZ.
 *         Für die Nutzung in einem bestimmten Projekt muss daher das ApllicationObjekt per CAST jübergeben werden.
 *         Z.B. für IApplicationUserOVPN
 * 
 * @author Fritz Lindhauer, 25.07.2023, 07:50:15
 * 
 */
public class HtmlTableHandlerByJobMainZZZ extends AbstractMainZZZ implements IApplicationUserZZZ{	
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
				System.out.println(ez.getDetailAllLast());
				ez.printStackTrace();						
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
				String[]saFlag = {IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name(),IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(), IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH.name(),IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name(),IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name(),IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name(), IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name(),IKernelCallIniSolverZZZ.FLAGZ.USECALL.name(), IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA.name()};
				IKernelConfigZZZ objConfig = new ConfigHtmlTableHandlerByJobZZZ(saArg, saFlag);
				IKernelZZZ objKernel = new KernelZZZ(objConfig, (String) null); //Damit kann man über die Startparameter ein anders konfiguriertes Kernel-Objekt erhalten.
				this.setKernelObject(objKernel);
								
				//############
				IApplicationZZZ objApplication = new HtmlTableHandlerApplicationZZZ(objKernel, null);
				objApplication.setMainObject(this);
				this.setApplicationObject(objApplication);
				
				//TEST:
				String sUrl = objApplication.getUrlToParse();
				System.out.println("URL to parse ='" + sUrl + "'");
				
				//############## DAS UI handlebar machen
				//### 1. Voraussetzung: OpenVPN muss auf dem Rechner vorhanden sein. Bzw. die Dateiendung .ovpn ist registriert. 
				//this.objClientTray = new ClientTrayUIZZZ(objKernel, this.objClientMain, (String[]) null);
				

				//############## DAS BACKEND handlebar machen, nutze anderes Modul, also eine andere ini-Datei...
				//Wie die JobSteps, so muss auch der Job das Modul in einer anderen ini-Datei haben, also das andere Kernel-Objekt!!!
												
				//### Arbeite mit einem JOB, der in einem anderen Projekt liegt. Also auch eine andere ini - Datei hat				
				IKernelConfigZZZ objConfigTableHandlerModule = new ConfigHtmlTableHandlerZZZ();
				String sProjectPathCalling=objConfig.getProjectPath();//Die Verknüpfung zur "Startapplikation".
				objConfigTableHandlerModule.setCallingProjectPath(sProjectPathCalling);//Der aufrufende Pfad ist dann zur Errechnung der Projektpfade wichtig
				
				IKernelZZZ objKernelTableHandlerModule = new KernelZZZ(objConfigTableHandlerModule, (String) null); //Damit kann man über die Startparameter ein anders konfiguriertes Kernel-Objekt erhalten.				
				IJobZZZ objJobHtml = new JobHtmlProviderZZZ(objKernelTableHandlerModule, objApplication);
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
						try {
							objLog.WriteLineDate(ez.getDetailAllLast());
						} catch (ExceptionZZZ ez2) {
							ez2.printStackTrace();
							System.out.println(ez2.getDetailAllLast());
						}
					}else{
						try {
							this.logProtocolString(ez.getDetailAllLast());
						} catch (ExceptionZZZ ez2) {
							ez2.printStackTrace();
							System.out.println(ez2.getDetailAllLast());
						}
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
				this.getKernelObject().getLogObject().WriteLineDate(e.getDetailAllLast());
			} catch (ExceptionZZZ e1) {	
				System.out.println(e1.getDetailAllLast());
				e1.printStackTrace();
			}			
		}
	}
	
}//END class
