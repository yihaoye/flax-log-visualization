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
    var viewerHeight = 450;

    var selectNode;
    var selectValue;
    var paths = [];


    var tree = d3.layout.tree()
        .size([viewerHeight, viewerWidth]);

    // define a d3 diagonal projection for use by the node paths later on.
    var diagonal = d3.svg.diagonal()
        .projection(function(d) {
            return [d.y, d.x];
        });

    var div = d3.select("body")
        .append("div") // declare the tooltip div
        .attr("class", "tooltip")
        .style("opacity", 0);

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
treeJSON = d3.json("JSONFiles/action_layer_tree.json", function(error, treeData) {

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
    var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 3]).on("zoom", zoom);

    // define the baseSvg, attaching a class for styling and the zoomListener
    var baseSvg = d3.select("#tree-container").append("svg")
        .attr("width", viewerWidth)
        .attr("height", viewerHeight)
        .attr("class", "overlay")
        .call(zoomListener);

    // Append a group which holds all nodes and which the zoom Listener can act upon.
    var svgGroup = baseSvg.append("g");


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

        $(".select").val("");
    };
    var outCircle = function(d) {
          
    };

    function onchange() {
      selectValue = d3.select('select').property('value');
      selectValue = selectValue.substring(0, selectValue.indexOf(' '));//"lvl2 &s=XXX : 1", remove " : 1"

      clean_paths(paths);

      for(var next_action in selectNode.s1_querys[selectValue].next_ActionsQuerys){
          paths = [];
          paths = searchTree(selectNode, next_action,[]); //middle:  selectNode.s1_querys[selectValue]
            if(typeof(paths) !== "undefined"){
                openPaths(paths);
            }
            else{
                alert(" not found!");
            }
      }
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
            d.y = (d.depth * (maxLabelLength * 7)); //maxLabelLength * 10px, "d.depth" automatically added to tree.nodes(root) by tree method
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

        nodeEnter.append("circle")
            .attr('class', 'nodeCircle')
            .attr("r", 0)
            .style("fill", function(d) {
                return d._children ? "lightsteelblue" : "#fff";
            })
            .on("mouseover", function(d) {
                overCircle(d);
            })
            .on("mouseout", function(d) {
                //outCircle(d);
            });

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


        // Change the circle fill depending on whether it has children and is collapsed
        node.select("circle.nodeCircle")
            .attr("r", 4.5)
            .style("fill", function(d) {
                if(d.class === "found"){
                  return "#ff4136"; //red
                }
                else if(d._children){
                  return "lightsteelblue";
                }
                else{
                  return "#fff";
                }
              })
              .style("stroke", function(d) {
                if(d.class === "found"){
                  return "#ff4136"; //red
                }
            });

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

    var select = d3.select('body')
      .append('select')
        .attr('class','select')
        .on('change',onchange);

    //d3.select(self.frameElement).style("height", "800px");

});