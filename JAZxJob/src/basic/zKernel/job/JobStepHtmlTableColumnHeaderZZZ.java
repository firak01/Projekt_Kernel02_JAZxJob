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
	
	public JobStepHtmlTableColumnHeaderZZZ(JobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepHtmlTableWriterNew_();
	}
	private boolean JobStepHtmlTableWriterNew_() throws ExceptionZZZ {
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
				//1. Hole die Interne Application für diesen Step.
	            //	 Dazu ist in der Ini Datei dieser STEP mit seinem Aliasnamen definiert (also NICHT die Klasse incl. Packagenamen) als ein Program.
	            //	 Die Werte dann im Program hinterlegen, also Pfad				
				IJobStepControllerZZZ objController = this.getJobStepController();
				IJobZZZ objJob = objController.getJob();
				
				IApplicationZZZ objApplication = objJob.getApplicationObject();
				IKernelZZZ objKernel = objApplication.getKernelObject();
				
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
				this.addOutput("TableHeadLabel",objOutput);
				
				bReturn = true;		
			} catch (Exception e) {
				e.printStackTrace();
			}									
		}//end main:
		return bReturn;
	}

	
}
