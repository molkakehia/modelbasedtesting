    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package com.telnet.modelbasedtesting.service;

    import com.telnet.modelbasedtesting.entities.Simulinkmodel;
    import com.telnet.modelbasedtesting.dao.SimulinkModelDao;
    import com.telnet.modelbasedtesting.dao.PersistenceProvider;
    import com.telnet.modelbasedtesting.entities.Inportvariable;
    import com.telnet.modelbasedtesting.entities.Outportvariable;
    import com.telnet.modelbasedtesting.entities.Result;
    import com.telnet.modelbasedtesting.entities.Testcase;
    import com.telnet.modelbasedtesting.testmodel.ISimulateModel;
    import com.telnet.modelbasedtesting.testmodel.SimulateModel;
    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.InputStream;
    import java.util.ArrayList;
    import java.util.Iterator;

    import java.util.List;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import matlabcontrol.MatlabConnectionException;
    import matlabcontrol.MatlabInvocationException;
    import org.apache.poi.hssf.usermodel.HSSFCell;
    import org.apache.poi.hssf.usermodel.HSSFRow;
    import org.apache.poi.hssf.usermodel.HSSFSheet;
    import org.apache.poi.hssf.usermodel.HSSFWorkbook;

    /**
     *
     * @author Hanen LAFFET
     */
    public class SimulinkModelServiceImpl implements SimulinkModelService {

        public static final String PERSISTENCE_UNIT_NAME = "oacaPU";
        public static final String descriptionModel = "C:\\Modèles_Valeo_à_tester\\Detect.mdl";
        //final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
        private SimulinkModelDao simulinkModelDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getSimulinkModelDao();
        private ISimulateModel simulateModel = new SimulateModel();
        private InportVariableService inportVariableService = new InportVariableServiceImpl();
        private OutportVariableService outportVariableService = new OutportVariableServiceImpl();
        private ResultService resultService = new ResultServiceImpl();

            //a supprimer
        @Override
        public Simulinkmodel findByid(Integer id) {
            Simulinkmodel model = null;
                    //UserDao userDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getUserDao();

            model = simulinkModelDao.findById(id);
                    //LOGGER.debug("Find User : Search user with id :" + id + "  " + user.toString());

            return model;
        }

        @Override
        public void createModel(Simulinkmodel model) {
            model.setDescriptionModel(descriptionModel);
            simulinkModelDao.add(model);
            System.out.println("    Model created : in method createModel");
            analyseModel(model.getDescriptionModel()); //pour avoir le modelPath

            //LOGGER.debug("Add User : User is added with success " + user.toString());
        }

        @Override
        public void updateModel(Simulinkmodel model) {
            //UserDao userDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getUserDao();
            simulinkModelDao.update(model);
            //LOGGER.debug("Update User : User is modified with success " + user.toString());
        }

        @Override
        public void deleteModel(Simulinkmodel model) {
            //UserDao userDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getUserDao();
            simulinkModelDao.remove(model);
            // LOGGER.debug("Delete User : User is deleted");
        }

        @Override
        public List<Simulinkmodel> findAll() {
            List<Simulinkmodel> modelList = simulinkModelDao.findAll();
            //LOGGER.debug("Get Users list :" + userList.toString());
            return modelList;
        }

        @Override
        public void analyseModel(String modelPath) {
            /*  SimulinkModelService modelService = new SimulinkModelServiceImpl();

             Simulinkmodel smodel= new Simulinkmodel();
             smodel.setNameModel("FastCore");
             modelService.createModel(smodel);*/

            //**HL** get the last model in database
            System.out.println("    findAll().size()" + findAll().size());
            Simulinkmodel smodel = findAll().get(findAll().size() - 1);

            ArrayList<ArrayList<String>> listInportOutportVariables = simulateModel.modelVariablesInportOutport(modelPath);
            ArrayList<String> inportVariablesNames = listInportOutportVariables.get(0);
            ArrayList<String> outportVariablesNames = listInportOutportVariables.get(1);

            ArrayList<ArrayList<String>> listVariablesSpecifications = simulateModel.modelVariablesSpecifications(modelPath);
            ArrayList<String> inportVariablesTypes = listVariablesSpecifications.get(0);
            ArrayList<String> inportVariablesDimensions = listVariablesSpecifications.get(1);

            ArrayList<String> outportVariablesTypes = listVariablesSpecifications.get(2);
            ArrayList<String> outportVariablesDimensions = listVariablesSpecifications.get(3);

            //**HL** Add inportVariaable to database
            int indexIterator = 0;
            for (Iterator<String> it = inportVariablesNames.iterator(); it.hasNext();) {

                String varName = it.next();
                Inportvariable inportVar = new Inportvariable();
                inportVar.setNameInport(varName);
                inportVar.setDataTypeInport(inportVariablesTypes.get(indexIterator));
                inportVar.setPortDimensionInport(inportVariablesDimensions.get(indexIterator));
                inportVar.setSimulinkModelidModel(smodel);

                inportVariableService.createVariable(inportVar);
                System.out.println("    Done: InportVariable added");
                indexIterator++;
            }
            //**HL** Add outportVariable to database
            int indexIterator1 = 0;
            for (Iterator<String> it = outportVariablesNames.iterator(); it.hasNext();) {
                Outportvariable outportVar = new Outportvariable();
                outportVar.setNameOutport(it.next());
                outportVar.setDataTypeOutport(outportVariablesTypes.get(indexIterator1));
                outportVar.setPortDimensionOutport(outportVariablesDimensions.get(indexIterator1));
                outportVar.setSimulinkModelidModel(smodel);

                outportVariableService.createVariable(outportVar);
                System.out.println("    Done: OutportVariable added");
                indexIterator1++;
            }

        }

        public void readTestCaseFile(String testCasesFileXlsPath, int idmodel) throws FileNotFoundException, IOException {
                // Find last row number of file testCasesFileXls
            idmodel=147;
            InputStream myxls = new FileInputStream(testCasesFileXlsPath);
            HSSFWorkbook book = new HSSFWorkbook(myxls);
            HSSFSheet sheet = book.getSheetAt(0);
            int noOfRows = sheet.getLastRowNum();

            //Find number of columns 
            int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            InportVariableService inportVar = new InportVariableServiceImpl();
            List<Inportvariable> listInportVar = inportVar.findAllByIdSimulink(idmodel);
            ArrayList<ArrayList<String>> testData0 = new ArrayList<ArrayList<String>>();
            for (int colIndex = 0; colIndex < noOfColumns; colIndex++) {
                int numerotest = 0;
                ArrayList<String> testData = new ArrayList<String>();
                int verif = 0; // pour ne pas repeter les cas de tests tant le nbre de col
                for (int rowIndex = 1; rowIndex <= noOfRows; rowIndex++) {

                    HSSFRow row = sheet.getRow(rowIndex);

                    if (row != null) {
                        String key = "";
                        HSSFCell cell = row.getCell(colIndex);
                        if (cell != null) {
                            try {
                                key = cell.getStringCellValue(); System.out.println(" key"+key);
                            } catch (java.lang.IllegalStateException e) {
                                key = String.valueOf(cell.getNumericCellValue()); System.out.println(" key"+key);
                            }
                            testData.add(key);

                            Testcase t = new Testcase();
                            t.setNumeroTestCase(String.valueOf(numerotest));
                            t.setValueTestCase(key);
                            t.setInportvariableidInportVariable(listInportVar.get(colIndex));
                            TestCaseService testCaseService = new TestCaseServiceImpl();
                            testCaseService.createTestCase(t);
                        }
                    }
                }
                testData0.add(testData);
                System.out.println("testData =" + testData);

            }

        }

        @Override
        public void generateExpectedOutput(String testCasesFileXlsPath, String modelPath) {
            File scriptFile = new File("C:\\modelBasedTesting_19_08\\modelBasedTesting\\modelBasedTesting2-www\\tmp\\F_ExpectedOutput.m");
            try {
                File expectedOutputResultFile = simulateModel.expectedOutputScript(scriptFile, testCasesFileXlsPath, modelPath);
                try {
                    simulateModel.runScriptMatlab(expectedOutputResultFile);
                    System.out.println("    fin proxy matlab run Expected output result file ");

                        // read txt and save in database
                    try {
                        System.out.println("  user. dir " + System.getProperty("user.dir") + "\\tmp\\F_ExpectedOutput.txt");
                        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\tmp\\F_ExpectedOutput.txt"));
                        String sCurrentLine;

                        while ((sCurrentLine = br.readLine()) != null) {
                            System.out.println(sCurrentLine);
                                    //search model
                            // read outputvariable: name , type , port dimension
                            // save result

                            //create model
                            SimulinkModelService modelService = new SimulinkModelServiceImpl();
                          //  SimulinkModelDao modelDao = new SimulinkModelDao();
                            Simulinkmodel smodel = new Simulinkmodel();
                            smodel.setNameModel("Detect");
                           // modelService.createModel(smodel);    // l'erreur ici !!!!!!!
                            simulinkModelDao.add(smodel);
                            //create outportVariable
                            Outportvariable outVar = new Outportvariable();
                            outVar.setNameOutport("test");
                            outVar.setSimulinkModelidModel(smodel);
                            outportVariableService.createVariable(outVar);

                            String[] splited = sCurrentLine.split("\\s+");

                            Result r0 = new Result();
                            r0.setExpectedOutput("[ " + splited[1] + "  " + splited[2] + "  " + splited[3] + "  " + splited[4] + " ]");
                            // Outportvariable outVar= out.findByid(13);
                            r0.setOutportVariableidOutportVariable(outVar);
                            resultService.createVariable(r0);
                            System.out.println(" r0 " + r0.toString());

                            Result r1 = new Result();
                            r1.setExpectedOutput("[ " + splited[5] + "  " + splited[6] + "  " + splited[7] + "  " + splited[8] + "  " + splited[9] + "  " + splited[10] + " ]"); //" ]");//
                            //Outportvariable outVar1= out.findByid(14);
                            r1.setOutportVariableidOutportVariable(outVar);
                            resultService.createVariable(r1);
                            System.out.println(" r1 " + r1.toString());

                            Result r2 = new Result();
                            r2.setExpectedOutput("[ " + splited[11] + "  " + splited[12] + "  " + splited[13] + "  " + splited[14] + " ]");
                            // Outportvariable outVar2= out.findByid(15);
                            r2.setOutportVariableidOutportVariable(outVar);
                            resultService.createVariable(r2);
                            System.out.println(" r2 " + r2.toString());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (MatlabInvocationException ex) {
                    Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MatlabConnectionException ex) {
                    Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void loadModel(String modelPath) {
            System.out.println("  into SimulinkModelServiceImpl CORE  ");
            modelPath = "C:\\Modèles_Valeo_à_tester\\Detect.mdl";
            String script = "cd " + System.getProperty("user.dir") + "\\tmp ;\n"
                    + "load_system('" + modelPath + "');\n"
                    + "slwebview('F')";
            try {
                //Create file for saving the matlab script 
                File file = new File(System.getProperty("user.dir") + "\\tmp\\F_loadModelScript.txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(script);
                bw.close();
                System.out.println("done: writing script in file");

                simulateModel.runScriptMatlab(file);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (MatlabInvocationException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MatlabConnectionException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void verifyModel(String modelPath) {
            System.out.println("  into SimulinkModelServiceImpl CORE  ");
            modelPath = "C:\\Modèles_Valeo_à_tester\\Detect.mdl";
            String script = "cd " + System.getProperty("user.dir") + "\\tmp ;\n"
                    + "load_system('" + modelPath + "');\n";
            script = script + "ModelAdvisor('F');\n";
            try {
                //Create file for saving the matlab script 
                File file = new File(System.getProperty("user.dir") + "\\tmp\\F_verifyModelScript.txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(script);
                bw.close();
                System.out.println("done: writing script in file");

                simulateModel.runScriptMatlab(file);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (MatlabInvocationException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MatlabConnectionException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void generateCodeC(String modelPath) {
            System.out.println("  into SimulinkModelServiceImpl CORE generateCodeC ");
            modelPath = "C:\\Modèles_Valeo_à_tester\\Detect.mdl"; ;
            String script = "cd " + System.getProperty("user.dir") + "\\tmp ;\n"
                    + "load_system('" + modelPath + "');\n";
            script = script + "cs = getActiveConfigSet('F');\n"
                    + "openDialog(cs);\n";   //a modifier F il faut générique
            try {
                //Create file for saving the matlab script 
                File file = new File(System.getProperty("user.dir") + "\\tmp\\F_generateCodeCScript.txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(script);
                bw.close();
                System.out.println("done: writing script in file");

                simulateModel.runScriptMatlab(file);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (MatlabInvocationException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MatlabConnectionException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void generateTestSuite(String modelPath) {
            String testCasesFileXlsPath = "C:\\Users\\Molka\\Desktop\\Projet PFE\\Code Hanen\\modelBasedTesting_19_08\\modelBasedTesting\\integration_testCase.xls";
            modelPath= "C:\\Users\\Molka\\Desktop\\Modèles_Valeo_à_tester\\Detect.mdl";
            int idmodel = 0;
            try {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                readTestCaseFile(testCasesFileXlsPath, idmodel);
                generateExpectedOutput(testCasesFileXlsPath,modelPath);
            } catch (IOException ex) {
                Logger.getLogger(SimulinkModelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
