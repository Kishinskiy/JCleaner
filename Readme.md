# JCleaner
## the program will delete the specified files exceeding the specified size from the specified directory if the disk is full up to a certain value

### Configurate you parameter in "config.properties" file

Example:
```editorconfig
diskName = /  - Name of Disk for Check usage space
maxDiskUsage = 20 - Maximum usage space in percents
path = /Users/kishinskiy/Downloads - Path for search files
maxFileSize = 4163500 - maximum file size in bites
fileType = .pdf - type files for detete
```