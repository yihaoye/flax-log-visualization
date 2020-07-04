/*Copyright (c) 2013-2016, 
All rights reserved.

the code is modified from work of Rob Schmuecker's drag tree js file
*/

//basically a way to get the path to an object
  function searchTree(obj,search,path){ //"obj" is the search path root, the result of the function is arrat of nodes (obj in array[0], search in array[last]), nodes obtain several parameters
    if(obj.name === search){ //if search is found return, add the object to the path and return it
      path.push(obj);
      return path;
    }
    else if(obj.children || obj._children){ //if children are collapsed d3 object will have them instantiated as _children
      var children = (obj.children) ? obj.children : obj._children;
      for(var i=0;i<children.length;i++){
        path.push(obj);// we assume this path is the right one
        var found = searchTree(children[i],search,path);
        if(found){// we were right, this should return the bubbled-up path from the first if statement
          return found;
        }
        else{//we were wrong, remove this parent from the path and continue iterating
          path.pop();
        }
      }
    }
    else{//not the right object, return false so it will continue to iterate in the loop
      return false;
    }
  }




  /*********************************************************************************/

  

  // Calculate total nodes, max label length
    var totalNodes = 0;
    var maxLabelLength = 0;
    // Misc. variables
    var i = 0;
    var duration = 750;
    var root;
    var querys = [];

    var margin = {top: 20, right: 120, bottom: 20, left: 120};

    // size of the diagram
    var viewerWidth = 1000;
    var viewerHeight = 420;

    var selectNode;
    var selectValue;
    var paths = [];

    var pathData;


    var tree = d3.layout.tree()
        .size([viewerHeight, viewerWidth]);

    // define a d3 diagonal projection for use by the node paths later on.
    var diagonal = d3.svg.diagonal()
        .projection(function(d) {
            return [d.y, d.x];
        });


    // A recursive helper function for performing some setup by walking through all nodes
    function visit(parent, visitFn, childrenFn) {
        if (!parent) return;

        visitFn(parent);

        var children = childrenFn(parent);
        if (children) {
            var count = children.length;
            for (var i = 0; i < count; i++) {
                visit(children[i], visitFn, childrenFn);
            }
        }
    }

    
    



// Get JSON data
treeJSON = d3.json("json/action_trace_tree.json", function(error, treeData) {

    //read path json data
    d3.json("json/users_actions_path.json", function(error, JSONdata){
        pathData = JSONdata;
        //console.log(pathData);
    });

    //console.log(treeData);

    // Call visit function to establish maxLabelLength
    visit(treeData, function(d) {
        totalNodes++;
        maxLabelLength = Math.max(d.name.length, maxLabelLength);
    }, function(d) {
        return d.children && d.children.length > 0 ? d.children : null;
    });
    

    // Define the zoom function for the zoomable tree
    function zoom() {
        svgGroup.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
    }

    // define the zoomListener which calls the zoom function on the "zoom" event constrained within the scaleExtents
    var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 10]).on("zoom", zoom);

    // define the baseSvg, attaching a class for styling and the zoomListener
    var baseSvg = d3.select("#tree-container").append("svg")
        .attr("width", viewerWidth)
        .attr("height", viewerHeight)
        .attr("class", "overlay")
        .call(zoomListener);

    // Append a group which holds all nodes and which the zoom Listener can act upon.
    var svgGroup = baseSvg.append("g");


    //
    var createTooltips = function(the_node, the_path){
        
        var tooltip_group = svgGroup.append("g")

        //load each action of the path (path for the userID and selectNode)
        for(var key in the_path){

            //
            for(var key_node in the_node.children){
                if(the_node.children[key_node].name == key){
                    the_node = the_node.children[key_node];
                    //console.log(the_node.children[key_node]);
                    break;
                }               
            }

            //tooltip block for each action
            tooltip_group.append('rect')
                    .attr('width', 100)
                    .attr('height', 100)
                    .attr("class", "tooltip")
                    .transition().duration(500)
                    .attr('x', the_node.y0)
                    .attr('y', the_node.x0)
                    .attr('rx', 10)
                    .attr('ry', 10)
                    .style("fill", "lightsteelblue")
                    .style("fill-opacity", "0.8");

            //tooltip text for one action, write nothing just used for wrap tspan
            var tooltip_text = tooltip_group.append('text')
                    .attr("class", "tooltip")
                    .attr('x', the_node.y0 + 7) //can be remove, since tspan has assigned attr "x"
                    .attr('y', the_node.x0 + 5);

            //tspan: new line break for each query within one action
            for(var _query in the_path[key]){
                tooltip_text.append("tspan")
                    .text(the_path[key][_query])
                    .style("font-size","10px")
                    .attr("dy", i ? "1.2em" : 0)
                    .attr("x", the_node.y0 + 7);
            }
        }

    }

    //
    var cleanTooltips = function(){
        $('.tooltip').remove();
    }

    var overCircle = function(d) {
        selectNode = d;

        querys = [];
        for(var key in d.s1_querys){
            if(d.s1_querys.hasOwnProperty(key)){
                querys.push(key + "  :  " + d.s1_querys[key].account);
            }
        }

        $(".select").empty();
        var options = d3.select(".select")
          .selectAll('option')
          .data(querys).enter()
          .append('option')
            .text(function (d) { return d; });

        $(".select").val(""); //dropdown list default text
    };

    var outCircle = function(d) {
          
    };

    function onchange() {
        selectValue = d3.select('select').property('value');
        var userID = selectValue.substring(0, selectValue.indexOf('s1.query')-1);//"XXXX-XX-XX uid=XXX s1.query=XXX : 1", remove "s1.query=XXX : 1"
        var s1query = selectValue.substring(selectValue.indexOf('s1.query'), selectValue.indexOf(':')-1);//get "s1.query=XXX"

        clean_paths(paths);
        cleanTooltips();

        ////////////////////////////////////////////////////////////////////////////
        var the_user_actions = []; 
        
        the_user_actions = pathData[userID];
        
        for(var key in the_user_actions){ //begin the path from selectNode
            if(key == selectNode.name){
                break;
            }
            delete the_user_actions[key]; 
        }

        console.log(the_user_actions);
        ////////////////////////////////////////////////////////////////////////////
      
        var next_action;
        for(next_action in the_user_actions){};
        paths = [];
        paths = searchTree(selectNode, next_action,[]); //
        if(typeof(paths) !== "undefined"){
            openPaths(paths);
        }
        else{
            alert(" not found!");
            paths = [];
        }

        createTooltips(selectNode, the_user_actions);
        ////////////////////////////////////////////////////////////////////////////
        
    };
    
    function openPaths(paths){
        for(var i =0;i<paths.length;i++){
          if(paths[i].id !== "1"){//i.e. not root
            paths[i].class = 'found';
            if(paths[i]._children){ //if children are hidden: open them, otherwise: don't do anything
              paths[i].children = paths[i]._children;
                paths[i]._children = null;
            }
            update(paths[i]);
          }
        }
    }

    function clean_paths(paths){
        for(var i =0;i<paths.length;i++){
          if(paths[i].id !== "1"){//i.e. not root
            paths[i].class = '';
            if(paths[i]._children){ //if children are hidden: open them, otherwise: don't do anything
              paths[i].children = paths[i]._children;
                paths[i]._children = null;
            }
            update(paths[i]);
          }
        }
    }

    // Function to center node when clicked/dropped so node doesn't get lost when collapsing/moving with large amount of children.

    function centerNode(source) {
        scale = zoomListener.scale();
        x = -source.y0;
        y = -source.x0;
        x = x * scale + viewerWidth / 2;
        y = y * scale + viewerHeight / 2;
        d3.select('g').transition()
            .duration(duration)
            .attr("transform", "translate(" + x + "," + y + ")scale(" + scale + ")");
        zoomListener.scale(scale);
        zoomListener.translate([x, y]);
    }

    // Toggle children function
    //把node收起来和展开，使用d.children、d._children来表示收起、展开状态
    function toggleChildren(d) {
        if (d.children) {
            d._children = d.children;
            d.children = null;
        } else if (d._children) {
            d.children = d._children;
            d._children = null;
        }
        return d;
    }

    // Toggle children on click.

    function click(d) {
        //if (d3.event.defaultPrevented) return; // click suppressed, check if is drag
        d = toggleChildren(d);
        cleanTooltips();
        update(d);
        centerNode(d);
    }

    function update(source) {
        // Compute the new height, function counts total children of root node and sets tree height accordingly.
        // This prevents the layout looking squashed when new nodes are made visible or looking sparse when nodes are removed
        // This makes the layout more consistent.
        var levelWidth = [1];
        var childCount = function(level, n) {
            if (n.children && n.children.length > 0) {
                if (levelWidth.length <= level + 1) levelWidth.push(0);

                levelWidth[level + 1] += n.children.length;
                n.children.forEach(function(d) {
                    childCount(level + 1, d);
                });
            }
        };
        childCount(0, root);
        var newHeight = d3.max(levelWidth) * 25; // 25 pixels per line  
        tree = tree.size([newHeight, viewerWidth]);

        // Compute the new tree layout.
        var nodes = tree.nodes(root).reverse(), //get a reverse odered array of "tree.nodes(root);"
            links = tree.links(nodes);

        //console.log(nodes);
        //console.log(links);

        // Set widths between levels based on maxLabelLength.
        nodes.forEach(function(d) {
            d.y = (d.depth * (maxLabelLength * 5)); //maxLabelLength * 10px, "d.depth" automatically added to tree.nodes(root) by tree method
        });

        // Update the nodes…
        node = svgGroup.selectAll("g.node")
            .data(nodes, function(d) {
                return d.id || (d.id = ++i);
            });

        // Enter any new nodes at the parent's previous position.
        var nodeEnter = node.enter().append("g")
            .attr("class", "node")
            .attr("transform", function(d) {
                return "translate(" + source.y0 + "," + source.x0 + ")";
            })
            .on('click', click);


        ///////////////////////////Outside circle///////////////////////////
        nodeEnter.append("circle")
            .attr('class', 'outsideCircle')
            .attr("r", 0)
            .style("fill", "steelblue")
            .on("mouseover", function(d) {
                overCircle(d);
                //cleanTooltips();
                update(d);
                //centerNode(d);
            })
            .on("mouseout", function(d) {
                //outCircle(d);
            });

        ///////////////////////////Inside circle///////////////////////////
        nodeEnter.append("circle")
            .attr('class', 'nodeCircle')
            .attr("r", 0)
            .style("fill", "#fff");
            

        nodeEnter.append("text")
            .attr("x", function(d) {
                return d.children || d._children ? -10 : 10;
            })
            .attr("dy", ".35em")
            .attr('class', 'nodeText')
            .attr("text-anchor", function(d) {
                return d.children || d._children ? "end" : "start";
            })
            .text(function(d) {
                return d.name;
            })
            .style("fill-opacity", 0);



        
        ///////////////////////////Outside circle///////////////////////////
        node.select("circle.outsideCircle")
            .attr("r", function(d){
                return 8; //size the node circle according to their access_percentage attribute!!!important function
            })
            .style("fill", function(d) {
                if(d.class === "found"){
                  return "#ff4136"; //red
                }
                else{
                  return "steelblue";
                }
            })
            .style("stroke", "none");
        


        ///////////////////////////Inside circle///////////////////////////
        // Change the circle fill depending on whether it has children and is collapsed
        node.select("circle.nodeCircle")
            .attr("r", function(d){
                //这个 7 是临时改的，原本是 8，再研究一下对不对
                return 7*d.access_percentage; //size the node circle according to their access_percentage attribute!!!important function
            })
            .style("fill", function(d) {
                if(d.class === "found"){
                  return "#ff4136"; //red
                }
                else{
                  return "#fff";
                }
            })
            .style("stroke", "none");


        // Transition nodes to their new position.
        var nodeUpdate = node.transition()
            .duration(duration)
            .attr("transform", function(d) {
                return "translate(" + d.y + "," + d.x + ")";
            });

        // Fade the text in
        nodeUpdate.select("text")
            .style("fill-opacity", 1);

        // Transition exiting nodes to the parent's new position.
        var nodeExit = node.exit().transition()
            .duration(duration)
            .attr("transform", function(d) {
                return "translate(" + source.y + "," + source.x + ")";
            })
            .remove();

        nodeExit.select("circle")
            .attr("r", 0);

        nodeExit.select("text")
            .style("fill-opacity", 0);

        // Update the links…
        var link = svgGroup.selectAll("path.link")
            .data(links, function(d) {
                return d.target.id;
            });

        // Enter any new links at the parent's previous position.
        link.enter().insert("path", "g")
            .attr("class", "link")
            .attr("d", function(d) {
                var o = {
                    x: source.x0,
                    y: source.y0
                };
                return diagonal({
                    source: o,
                    target: o
                });
            });

        // Transition links to their new position.
        link.transition()
            .duration(duration)
            .attr("d", diagonal)
            .style("stroke",function(d){
                if(d.source.class==="found" && d.target.class==="found"){
                  return "#ff4136";
                }
            })
            .style("stroke-width",function(d){
                return (5*d.target.access_percentage); // 用连线粗细暗示后续节点的访问百分比
            });

        // Transition exiting nodes to the parent's new position.
        link.exit().transition()
            .duration(duration)
            .attr("d", function(d) {
                var o = {
                    x: source.x,
                    y: source.y
                };
                return diagonal({
                    source: o,
                    target: o
                });
            })
            .remove();

        // Stash the old positions for transition.
        nodes.forEach(function(d) {
            d.x0 = d.x;
            d.y0 = d.y;
        });
    }//update()

    

    // Define the root
    root = treeData;
    root.x0 = viewerHeight / 2;
    root.y0 = 0;

    //一开始不展现完整树，让用户自己展开
    function collapse(d) {
        if (d.children) {
          d._children = d.children;
          d._children.forEach(collapse);
          d.children = null;
        }
    }
    root.children.forEach(collapse);

    // Layout the tree initially and center on the root node.
    update(root);
    centerNode(root);

    //add notice text
    var notice_text = d3.select('body')
        .append('html')
        .html("Please move the mouse over a node of tree and select item within the dropdown list below. Then the tree will show the user's actions path." + "<br>");

    // dropdown list
    var select = d3.select('body')
        .append('select')
        .attr('class','select')
        .on('change',onchange);

    //d3.select(self.frameElement).style("height", "800px");

});