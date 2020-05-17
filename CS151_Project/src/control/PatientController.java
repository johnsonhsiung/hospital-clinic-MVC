package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import model.Medicine;
import model.Patient;
import model.PatientList;
import model.PatientModel;
import view.*;



public class PatientController {
  
	
	static JFrame frame = new JFrame();
	private static PatientModel patientModel; 
	
	public PatientController(PatientModel model)
	{
		patientModel = model; 
	}

	public static void main(String[] args)
	{
		PatientModel model = new PatientModel(); 
		PatientController control = new PatientController(model); 
		MenuPage menu = new MenuPage(frame); 
		gotoReqInputListener r = new gotoReqInputListener();
		gotoDatabaseListener d = new gotoDatabaseListener();
		menu.addRegisterListener(r); 
		menu.addViewListener(d);
		
	
	}
	//add what the model should do with the information for each actionlistener
	//call the approrpiate actionlisteners for each page it goes to 
	//should check if the previous page's text is filled out
	static class gotoReqInputListener implements ActionListener
	{
		
		
		
		public void actionPerformed(ActionEvent e)
		{
			ReqInfoPage reqInfo = new ReqInfoPage(frame); 
			gotoMenuListener b = new gotoMenuListener();
			gotoOptInfoListener n = new gotoOptInfoListener(reqInfo); //maybe take previous page a parameter for action listeners to see if fields put in 
			reqInfo.addBackListener(b);
			reqInfo.addNextListener(n);
		}
	}
	static class gotoMenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			MenuPage menu = new MenuPage(frame); 
			
			gotoReqInputListener r = new gotoReqInputListener(); 
			gotoDatabaseListener d = new gotoDatabaseListener();
			menu.addRegisterListener(r);
			menu.addViewListener(d);
			
		}
		
	}
	static class gotoOptInfoListener implements ActionListener
	{
		ReqInfoPage reqInfo; 
		boolean isCheckReqInfo; 
		public gotoOptInfoListener(ReqInfoPage reqInfo)
		{
			this.reqInfo = reqInfo; 
			isCheckReqInfo = true; 
		}
		public gotoOptInfoListener()
		{
			isCheckReqInfo = false; 
		}
		public void actionPerformed(ActionEvent e)
		{
			
			if (isCheckReqInfo)
			{
				String[] info = reqInfo.getRequiredInfo();
				
				for (String a: info)
				{
					System.out.println(a);
					if (a.isEmpty())
					{
						return; 
						//error message 
					}
				}
			
				//adding a new patient into the patient list
				//what if you go next next and then come back again? Maybe have it delete the first patient everytime because if you dont 
				//need to check if texts are empty, then you are going back. Going back means you already created a new patient but now want to go back. 
			Patient patient = new Patient(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], 
					info[9], info[10], info[11], info[12], info[13], info[14]);
			
			patientModel.addPatient(patient);
				
			}
			
				
			OptInfoPage optInfo = new OptInfoPage(frame);
			gotoReqInputListener b = new gotoReqInputListener(); 
			gotoTinHypStatusListener n = new gotoTinHypStatusListener(optInfo); 
			
			//adding the rest of optional information of a patient

			
			optInfo.addBackListener(b);
			optInfo.addNextListener(n);
			
			
		}
		
	}
	static class gotoTinHypStatusListener implements ActionListener
	{
		OptInfoPage optInfo;
		boolean isCheckOptional; 
		public gotoTinHypStatusListener()
		{
			isCheckOptional = false; 
		}
		public gotoTinHypStatusListener(OptInfoPage optInfo)
		{
			this.optInfo = optInfo; 
			isCheckOptional = true; 
		}
		public void actionPerformed(ActionEvent e)
		{
			if (isCheckOptional)
			{
				Patient patient = patientModel.getPatient(patientModel.getSize()-1);
				patient.setOccupation(optInfo.getOccupation());
				patient.setWorkStatus(optInfo.getWorkStatus());
				patient.setEduDeg(optInfo.getEducationDegree());
				
			}
			TinHypInputPage tinHyp = new TinHypInputPage(frame);
			gotoOptInfoListener b = new gotoOptInfoListener(); 
			gotoInputMedListener n = new gotoInputMedListener(); 
			tinHyp.addBackListener(b);
			tinHyp.addNextListener(n);
			 
			
			
			
		}
		
	}
	static class gotoInputMedListener implements ActionListener
	{
		private InputMedPage inputMedPage; 
		private boolean isCheckMedicine; 
		public gotoInputMedListener()
		{
			isCheckMedicine = false; 
		}
		public gotoInputMedListener(InputMedPage page)
		{
			inputMedPage = page; 
			isCheckMedicine = true; 
		}
		public void actionPerformed(ActionEvent e)
		{
			if (isCheckMedicine)
			{
				String[] medInfo = inputMedPage.getRequiredInfo(); 
				for (String a: medInfo)
				{
					if(a.isEmpty())
					{
						//error
						return;
					}
					
				}
				Medicine med = new Medicine(medInfo[0],medInfo[1],medInfo[2],medInfo[3],medInfo[4],medInfo[5],medInfo[6],medInfo[7],medInfo[8]); 
				System.out.println(medInfo[0] + medInfo[1] + medInfo[2] + medInfo[3] + medInfo[4] + medInfo[5] + medInfo[6] + medInfo[7] + medInfo[8]);
				Patient patient = patientModel.getPatient(patientModel.getSize()-1);
				
				patient.addMedicine(med);
				for (Medicine a: patient.getMedicine())
				{
					System.out.println(a); 
				}
				
			}
			InputMedPage inputMedPage = new InputMedPage(frame);
			gotoTinHypStatusListener b = new gotoTinHypStatusListener();
			gotoInputMedListener m = new gotoInputMedListener(inputMedPage);
			inputMedPage.addBackListener(b);
			inputMedPage.addNextListener(m);
		}
		
	}
	static class gotoDatabaseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("hi");
			PatientDataPage database = new PatientDataPage(frame);
			gotoMenuListener m = new gotoMenuListener();
			gotoReqInputListener n = new gotoReqInputListener();
			database.addBackListener(m);
			database.addNextListener(n);
		}
		
	}
		
}