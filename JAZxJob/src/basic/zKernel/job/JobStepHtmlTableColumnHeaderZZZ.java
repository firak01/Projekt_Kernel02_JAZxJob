package basic.zKernel.job;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.net.client.IApplicationZZZ;
import basic.zKernel.net.client.IMainZZZ;
import custom.zKernel.LogZZZ;
import use.zKernel.html.step01.writer.KernelWriterHtmlByXsltZZZ;
import use.zKernel.html.step01.writer.TableHeadZZZ;
import use.zKernel.html.step02.reader.Debug02_ReaderHtmlTableZZZ;

public class JobStepHtmlTableColumnHeaderZZZ extends AbstractJobStepWithOutputZZZ {
	public static String sJOBSTEP_ALIAS="HtmlTableColumnHeaderStep";
	
	public JobStepHtmlTableColumnHeaderZZZ() throws ExceptionZZZ {
		super();
	}
	
	/**Ein JobStep, der im gleichen Modul liegt, also das gleiche Kernel-Objekt wie Application nutzt
	 * @param objController
	 * @throws ExceptionZZZ
	 * 23.06.2023, 09:44:32, Fritz Lindhauer
	 */
	public JobStepHtmlTableColumnHeaderZZZ(IJobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepHtmlTableWriterNew_(null);
	}
	
	/**Ein JobStep, der in einem anderen Modul liegt, also NICHT das gleiche Kernel-Objekt wie Application nutzt
	 * @param objController
	 * @throws ExceptionZZZ
	 * 23.06.2023, 09:44:32, Fritz Lindhauer
	 */
	public JobStepHtmlTableColumnHeaderZZZ(IKernelZZZ objKernel, IJobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepHtmlTableWriterNew_(objKernel);
	}
	
	private boolean JobStepHtmlTableWriterNew_(IKernelZZZ objKernel) throws ExceptionZZZ {
		if(objKernel!=null) {
			this.setKernelObject(objKernel);
		}
		return true;
	}
		
	@Override
	public String getJobStepAliasCustom() throws ExceptionZZZ {
		return sJOBSTEP_ALIAS;
	}
	
	@Override
	public boolean process() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String stemp;String sPropertyUsed;
			try {				
				//Momentan noch nicht gebraucht, 
				//aber wenn noch ein anderer Step vorgeschaltet wird,
				//wird das benötigt, um den Output des vorherigen Steps abzufragen.
				//IJobStepControllerZZZ objController = this.getJobStepController();
				//IJobZZZ objJob = objController.getJob();
				
				//1. Hole das Kernel-Objekt für diesen Step.
	            //	 Dazu ist in der Ini Datei dieser STEP mit seinem Aliasnamen definiert (also NICHT die Klasse incl. Packagenamen) als ein Program.
	            //	 Die Werte dann im Program hinterlegen, also Pfad				
				IKernelZZZ objKernel = this.getKernelObject(); //Das Kernel-Objekt aus dem JobStep holen				
				String sAlias = this.getJobStepAlias();
	 			
		        //Die Reihenfolge der Header, wie konfiguriert
		        sPropertyUsed = "TableHeaderMap";		        		       
		        Map<String,String> mapTableHeadLabel = objKernel.getParameterHashMapWithStringByProgramAlias(sAlias, sPropertyUsed);
				Set<String>setHeadId = mapTableHeadLabel.keySet();
				Iterator<String>itHeadId = setHeadId.iterator();
				
				//Aus der HashMap die Index HashMap mit den Table Head-Objekten errechnen.
				HashMapIndexedZZZ<Integer, TableHeadZZZ> mapIndexedTableHeadLabel = new HashMapIndexedZZZ<Integer, TableHeadZZZ>();				
				while(itHeadId.hasNext()) {
					String sHeadId = itHeadId.next();
					String sLabel = mapTableHeadLabel.get(sHeadId);
					TableHeadZZZ head = new TableHeadZZZ(sHeadId,sLabel);
				
					mapIndexedTableHeadLabel.put(head);
				}
				
				//Den Rueckgabewert bereitstellen fuer nachfolgende Jobs.
				JobStepOutputZZZ objOutput = new JobStepOutputZZZ(mapIndexedTableHeadLabel);
				this.addOutput("TableHeadLabelMap",objOutput);
				
				bReturn = true;		
			} catch (Exception e) {
				e.printStackTrace();
			}									
		}//end main:
		return bReturn;
	}	
}
