<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Список порогов отклонений</title>
        <link rel="stylesheet" href="js/dijit/themes/claro/claro.css">
	<style type="text/css">
            /*@import "js/dojox/grid/resources/claroGrid.css";*/
            
            @import "js/dojo/resources/dojo.css";
            @import "js/dijit/themes/claro/claro.css";
            @import "js/dojox/grid/enhanced/resources/claro/EnhancedGrid.css";
            @import "js/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";            
            
            #grid {
                width: 60em;
                height: 40em;
            }
            
            #createDialog_underlay {
                background-color: #000;
                opacity: 0.5;
            }
        </style>
        <script>dojoConfig = {parseOnLoad: true}</script>
        <script src="js/dojo/dojo.js" data-dojo-config="async: true"></script>
        <script>
        require(['dojo/data/ItemFileWriteStore', 'dojo/on', 'dojox/grid/EnhancedGrid', 'dojox/grid/enhanced/plugins/Pagination', 'dojo/domReady!'],
            function(ItemFileWriteStore, on, EnhancedGrid, Pagination){
                
                var ruFields;
                var dbFields;
                
                var data = {
                    items: []
                };
                
                var layout;
                
                dojo.xhr.post({
                    url: "requestFields.htm",
                    handleAs: "json",
                    load: function(result) {
                        ruFields = result[0];
                        dbFields = result[1];
                        layout = [[
                            {'name': ruFields[0], 'field': dbFields[0], 'width': '100px', 'editable': 'true'},
                            {'name': ruFields[1], 'field': dbFields[1], 'width': '100px', 'editable': 'true'},
                            {'name': ruFields[2], 'field': dbFields[2], 'width': '200px', 'editable': 'true'},
                            {'name': ruFields[3], 'field': dbFields[3], 'width': '150px', 'editable': 'true'}
                        ]];
                    
                        dojo.xhr.post({
                            url: "requestContent.htm",
                            handleAs: "json",
                            load: function(result) {
                                
                                for(var i=0; i<result.length; i++) {
                                    data.items.push(result[i]);
                                }
                                
                                var store = new ItemFileWriteStore({data: data});

                                var grid = new EnhancedGrid({
                                    id: 'grid',
                                    store: store,
                                    structure: layout,
                                    rowSelector: '20px',
                                    plugins: {
                                      pagination: {
                                          pageSizes: ["10", "25", "50", "100", "All"],
                                          description: true,
                                          sizeSwitch: true,
                                          pageStepper: true,
                                          gotoButton: true,
                                          maxPageStep: 2,
                                          position: "bottom"
                                      }
                                    }
                                });
                                
                                grid.placeAt("gridDiv");
                                grid.startup();
                                
                                postAdd(store);
                                postDelete(grid, store);
                                
                                dojo.connect(grid, "onApplyCellEdit", function()
                                {
                                    dojo.when(store.save(), function() {
                                        var selectedItem = grid.selection.getSelected()[0];
                                        dojo.xhr.post({
                                            url: "update.htm",
                                            handleAs: "json",
                                            content: {
                                                id: selectedItem.id,
                                                number: selectedItem.number,
                                                value: selectedItem.value,
                                                isActive: selectedItem.isActive
                                            },
                                            load: function(result) {
                                                dojo.require("dojox.widget.DialogSimple");
                                                dojo.ready(function(){
                                                     var dlg = new dojox.widget.DialogSimple({content:"Изменение принято", style: "200px"});
                                                     dlg.startup();
                                                     dlg.show();
                                                     setTimeout(function(){
                                                         dlg.hide();
                                                     }, 1000);
                                                });
                                            }
                                        });
                                    });
                                });
                                
                            }
                        });
                    }
                });
                
                function postAdd(store) {
                    on(addButton,'click',
                        function(e){
                            var reqVal = {
                                    number: dojo.byId("number").value,
                                    value: dojo.byId("value").value,
                                    isActive: dojo.byId("isActive").value
                                }
                            dojo.xhr.post({
                                url: "create.htm",
                                handleAs: "json",
                                content: reqVal,
                                load: function(result) {
                                    var myNewItem = {
                                            'id': result, 
                                            'number': reqVal.number, 
                                            'value': reqVal.value, 
                                            'isActive': reqVal.isActive
                                    };
                                    store.newItem(myNewItem);
                                }
                            });
                            createDialog.onCancel();
                        }
                    );
                }
                
                function postDelete(grid, store) {
                    on(removeRow,'click',
                        function(e){
                            var items = grid.selection.getSelected();
                            for (var i=0; i<items.length; i++) {
                                var selectedItem = items[i];
                                (function(selectedItem) { 
                                    dojo.xhr.post({
                                        url: "delete.htm",
                                        handleAs: "json",
                                        content: {
                                            id: selectedItem.id,
                                            number: selectedItem.number,
                                            value: selectedItem.value,
                                            isActive: selectedItem.isActive
                                        },
                                        load: function(result) {
                                            store.deleteItem(selectedItem);
                                        }
                                    });
                                })(selectedItem);
                            }
                        }
                    );
                }
        });
        </script>        
    </head>
    <body class="claro">
        <h1>Списки отклонений</h1>
        <div id="gridDiv"></div>
        
        <script>require(["dijit/Dialog", "dijit/form/TextBox", "dijit/form/Button", "dojo/parser"]);</script>
        
        <div data-dojo-type="dijit/Dialog" data-dojo-id="createDialog" id="createDialog" title="Добавить запись">
            <table class="dijitDialogPaneContentArea">
                <tr>
                    <td><label for="name">Номер измерительного датчика:</label></td>
                    <td><input data-dojo-type="dijit/form/TextBox" name="number" id="number"></td>
                </tr>
                <tr>
                    <td><label for="address">Значение аварийного порога отклонения:</label></td>
                    <td><input data-dojo-type="dijit/form/TextBox" name="value" id="value"></td>
                </tr>
                <tr>
                    <td><label for="address">Активный/Неактивный:</label></td>
                    <td><input data-dojo-type="dijit/form/TextBox" name="isActive" id="isActive"></td>
                </tr>
            </table>

            <div class="dijitDialogPaneActionBar">
                <button dojoType="dijit.form.Button" type="button" id="addButton" data-dojo-id="addButton">Добавить</button>
                <button dojoType="dijit.form.Button" type="button" onClick="createDialog.onCancel();"
                        id="cancelButton">Отмена</button>
            </div>
        </div>
        
        <button id="showAddDialog" data-dojo-type="dijit/form/Button" type="button">
            Добавить запись
            <script type="dojo/method" data-dojo-event="onClick" data-dojo-args="evt">
                require(["dijit/registry"], function(registry){
                    registry.byId("createDialog").show();
                });
            </script>
        </button>
        
        <button id="removeRow" data-dojo-type="dijit/form/Button" type="button">
            Удалить запись
        </button>        
        
    </body>
</html>
