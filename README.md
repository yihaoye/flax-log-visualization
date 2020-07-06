# FLAX System Log Visualization

This is an Open Source Project, 
which is a part of FLAX System (University of Waikato open source project).


## Briefly Introduction  
The FLAX log visualization system presents several data analyses with corresponding graph.  
The following examples are some of these graphs.  

This system can be divided into two parts: front-end and back-end:
The back-end applies Java program and MongoDB to process FLAX log data and output JSON files.
The front-end applies web tech (Bootstrap, jQuery, D3.js) to draw graphs depending on back-end's JSON file.
  
Overview Calendar of all users' behaviors: 

<img src="./1.png" height="250" width="500">

Relations among different users' behaviors: 

<img src="./2.png" height="250" width="500">

User's behavior trace tree: 

<img src="./3.png" height="250" width="500">
<img src="./4.png" height="250" width="500">

## Link
FLAX log visualization system interface: http://yihaoye.github.io/FLAX-log-visualization/front-end/  
Log records example: http://yihaoye.github.io/FLAX-log-visualization/usage-logs/example_usage.log  

## License
GNU General Public License 