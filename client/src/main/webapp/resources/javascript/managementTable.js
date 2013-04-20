jQuery(document).ready(function(){
	jQuery(".managementTable tbody tr").mouseover(function () { 
		jQuery(this).addClass("highlight"); 
	}); 
	
	jQuery(".managementTable tbody tr").mouseout(function () { 
		jQuery(this).removeClass("highlight"); 
	}); 
});