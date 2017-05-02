load_system('C:\Modèles_Valeo_à_tester\Detect.mdl');
t0=get_param('Detect/Detection_cliquetis','DataType');
d0=get_param('Detect/Detection_cliquetis','PortDimensions');
t1=get_param('Detect/Bruit_moyen_cyl','DataType');
d1=get_param('Detect/Bruit_moyen_cyl','PortDimensions');
t2=get_param('Detect/Knk_det_ris_knk_cyl_uavb1','DataType');
d2=get_param('Detect/Knk_det_ris_knk_cyl_uavb1','PortDimensions');
t3=get_param('Detect/Bvmp_inh_cliq_acquisition','DataType');
d3=get_param('Detect/Bvmp_inh_cliq_acquisition','PortDimensions');
t4=get_param('Detect/Detection_fort_cliquetis','DataType');
d4=get_param('Detect/Detection_fort_cliquetis','PortDimensions');
EE={t0,d0;t1,d1;t2,d2;t3,d3;t4,d4}
fileID = fopen('C:\modelBasedTesting_19_08\modelBasedTesting\modelBasedTesting-core\tmp\VariableTypeDimensionOutport.txt','w');
formatSpec = '%s %s \n';
[nrows,ncols] = size(EE);
for row = 1:nrows fprintf(fileID,formatSpec,EE{row,:}); end 
fclose(fileID); 