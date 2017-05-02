load_system('C:\Modèles_Valeo_à_tester\Detect.mdl');
blockPaths = find_system('Detect','Type','Block');
blockTypes = get_param(blockPaths,'BlockType');
BB=[blockPaths,blockTypes];
fileID = fopen('C:\modelBasedTesting_19_08\modelBasedTesting\modelBasedTesting-core\tmp\VariableNameType2.txt','w');
formatSpec = '%s %s \n';
[nrows,ncols] = size(BB);
for row = 1:nrows fprintf(fileID,formatSpec,BB{row,:}); end 
fclose(fileID); 
