/*
 itemCheckboxParent - class of checkbox which selects/deselects all rows
 itemCheckbox       - class of checkbox in row
 removeButton       - class of button which removes rows
 */
jQuery(document).ready(function(){
	jQuery(".managementTable tbody tr").mouseover(function () { 
		jQuery(this).addClass("highlight"); 
	}); 
	
	jQuery(".managementTable tbody tr").mouseout(function () { 
		jQuery(this).removeClass("highlight"); 
	});

    jQuery('.itemCheckboxParent').click(function(){
        var tableId = '#'+jQuery(this).parents('table').attr('id');
        var row = tableId + ' .itemCheckbox';
        if(jQuery(this).is(':checked')){
            jQuery(row).attr('checked','checked');
        }else{
            jQuery(row).removeAttr('checked');
        }
    });

    jQuery('.removeButton').click(function(){
        jQuery(this).parents('form').submit() ;
    });
});