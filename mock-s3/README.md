## flv mock object storage
  
Here using [fake-s3](https://github.com/jubos/fake-s3) to mock a local s3 server, for production we should use the real s3.  
  
```
gem install fakes3
fakes3 -r /mnt/fakes3_root -p 4567 --license YOUR_LICENSE_KEY
```
  
go to project root path and run the corresponding script which contains above commands.  
  
### future plan
Move to https://github.com/localstack/localstack if everything is fine.  
