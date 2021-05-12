$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});



///CLIENT-MODEL================================================================
function validatePaymentForm() {
	
    // CODE-------------------------------------
    if ($("#PaymentCode").val().trim() == "") {
        return "Insert Payment Code.";
    }
    // TYPE-------------------------------------
    if ($("#PaymentType").val().trim() == "") {
        return "Insert Payment Type.";
    }
    // TOTALPRICE-------------------------------
    if ($("#TotalPrice").val().trim() == "") {
        return "Insert Payment Price.";
    }
    // is numerical value
    var tmpPrice = $("#TotalPrice").val().trim();
    if (!$.isNumeric(tmpPrice)) {
        return "Insert a numerical value for Payment Price.";
    }
    // convert to decimal price
    $("#TotalPrice").val(parseFloat(tmpPrice).toFixed(2));
    
    // STATUS----------------------------------
    if ($("#PaymentStatus").val().trim() == "") {
        return "Insert Payment Status.";
    }
    // DATE------------------------------------
    if ($("#Date").val().trim() == "") {
        return "Insert Payment Date.";
    }
    // TIME------------------------------------
    if ($("#Time").val().trim() == "") {
        return "Insert Payment Time.";
    }
    return true;
}



///SAVE-BUTTON================================================================
$(document).on("click", "#btnSave", function (event) 
{
    // Clear alerts
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    
    // Form validation
    var status = validatePaymentForm();
    if (status != true) 
    {
        $("#alertError").text(status);
        $("#alertError").show();
        
        return;
    }
    
    // If valid
    var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "PaymentsAPI",
            type: type,
            data: $("#formPayment").serialize(),
            dataType: "text",
            complete: function (response, status) {
            	onPaymentSaveComplete(response.responseText, status);
            }
        });
});


function onPaymentSaveComplete(response, status) 
{
    	if (status == "success") 
    	{
    			var resultSet = JSON.parse(response);
    			
    			if (resultSet.status.trim() == "success") 
    			{
    					$("#alertSuccess").text("Successfully saved.");
	    				$("#alertSuccess").show();
	    				
	    				$("#divPaymentsGrid").html(resultSet.data);
    			} 
    			else if (resultSet.status.trim() == "error") 
    			{
    					$("#alertError").text(resultSet.data);
    					$("#alertError").show();
    			}
    	}
    	
    	else if (status == "error") 
    	{
    			$("#alertError").text("Error while saving.");
    			$("#alertError").show();
    	}	 
    	
    	else 
    	{
    			$("#alertError").text("Unknown error while saving..");
    			$("#alertError").show();
    	}
    	
    	$("#hidPaymentIDSave").val("");
    	$("#formPayment")[0].reset();
}


///UPDATE-BUTTON================================================================
$(document).on("click", ".btnUpdate", function (event) 
{
    	$("#hidPaymentIDSave").val($(this).data("paymentid"));
    	$("#PaymentCode").val($(this).closest("tr").find('td:eq(0)').text());
    	$("#PaymentType").val($(this).closest("tr").find('td:eq(1)').text());
    	$("#TotalPrice").val($(this).closest("tr").find('td:eq(2)').text());
    	$("#PaymentStatus").val($(this).closest("tr").find('td:eq(3)').text());
    	$("#Date").val($(this).closest("tr").find('td:eq(4)').text());
    	$("#Time").val($(this).closest("tr").find('td:eq(5)').text());
});


///DELETE-BUTTON================================================================
$(document).on("click", ".btnRemove", function (event) 
{
    $.ajax(
        {
            url: "PaymentsAPI",
            type: "DELETE",
            data: "PaymentID=" + $(this).data("paymentid"),
            dataType: "text",
            complete: function (response, status) 
            {
            	onPaymentDeleteComplete(response.responseText, status);
            }
        });
});


function onPaymentDeleteComplete(response, status) 
{
    	if (status == "success") 
    	{
    			var resultSet = JSON.parse(response);
    			
    			if (resultSet.status.trim() == "success") 
    			{
    					$("#alertSuccess").text("Successfully deleted.");
    					$("#alertSuccess").show();
    					
    					$("#divPaymentsGrid").html(resultSet.data);
    			}
    			
    			else if (resultSet.status.trim() == "error")
    			{
    					$("#alertError").text(resultSet.data);
    					$("#alertError").show();
    			}
    	} 
    	
    	else if (status == "error") 
    	{
    			$("#alertError").text("Error while deleting.");
    			$("#alertError").show();
    	} 
    	
    	else 
    	{
    			$("#alertError").text("Unknown error while deleting..");
    			$("#alertError").show();
    	}
}