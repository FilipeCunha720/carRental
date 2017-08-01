(function ($scope) {
    'use strict';

    angular
        .module('app')
        .controller('RentalCarController', RentalCarController);
    
    RentalCarController.$inject = ['$http'];
    function RentalCarController($http,$scope) {
        var vm = this;
        vm.isprintVehiclesOrderByPrice = false;
        vm.iscalculateSpecificationOfVehicles = false;
        vm.iscalculateHighestRatedSupplier = false;
        vm.iscalculateVehicleScore = false;
        vm.stores = [];
        vm.printVehiclesOrderByPrice = printVehiclesOrderByPrice;
        vm.calculateSpecificationOfVehicles = calculateSpecificationOfVehicles;
        vm.calculateHighestRatedSupplier = calculateHighestRatedSupplier;
        vm.calculateVehicleScore = calculateVehicleScore;

        function printVehiclesOrderByPrice(){
            var url = "/rentalCar/printVehiclesOrderByPrice";
            var storesPromise = $http.get(url);
            storesPromise.then(function(response){
                vm.stores = response.data;
            });
            vm.isprintVehiclesOrderByPrice = true;
            vm.iscalculateSpecificationOfVehicles = false;
            vm.iscalculateHighestRatedSupplier = false;
            vm.iscalculateVehicleScore = false;
        }

        function calculateSpecificationOfVehicles(){
        	var url = "/rentalCar/calculateSpecificationOfVehicles";
            var storesPromise = $http.get(url);
            storesPromise.then(function(response){
                vm.stores = response.data;
            });
            vm.isprintVehiclesOrderByPrice = false;
            vm.iscalculateSpecificationOfVehicles = true;
            vm.iscalculateHighestRatedSupplier = false;
            vm.iscalculateVehicleScore = false;
        }
        
        function calculateHighestRatedSupplier(){
        	var url = "/rentalCar/calculateHighestRatedSupplier";
            var storesPromise = $http.get(url);
            storesPromise.then(function(response){
                vm.stores = response.data;
            });
            
            vm.isprintVehiclesOrderByPrice = false;
            vm.iscalculateSpecificationOfVehicles = false;
            vm.iscalculateHighestRatedSupplier = true;
            vm.iscalculateVehicleScore = false;
        }
        
        function calculateVehicleScore(){
        	var url = "/rentalCar/calculateVehicleScore";
            var storesPromise = $http.get(url);
            storesPromise.then(function(response){
            	var json = JSON.stringify(response.data);
            	var arrayJson = json.split(",");
            	var list=[];
            	for (var i = 0; i < arrayJson.length; i++) { 
            	    var object = arrayJson[i].split(":");
            	    var carInfo = object[0].split("-");
            	    var car = {name:carInfo[0].replace(/"|{/g,''), score:carInfo[1], rating:carInfo[2],sum:object[1].replace(/"|}/g,'')};
            	    list.push(car);
            	}
                vm.stores = list;
            });
            
            vm.isprintVehiclesOrderByPrice = false;
            vm.iscalculateSpecificationOfVehicles = false;
            vm.iscalculateHighestRatedSupplier = false;
            vm.iscalculateVehicleScore = true;
        }
    }
})();