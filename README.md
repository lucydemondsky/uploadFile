# uploadFile
SpringBoot Application
start server: Open fl.com.Application  run main method.

Process:
1, error or successful message.

2, Front-end use form to post upload files to UploadController
  1) UploadController: requestParam"file" to get MultiPart files
  2) StorageService: Copy file into given directory.
  
3, A list of all files in the rootLocation(directory)
  1)UploadController: reflectingly call function of method of the same controller to map the result.
  2)StorageService: iterate all files from given directory
