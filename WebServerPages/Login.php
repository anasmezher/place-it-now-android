<?php
header("Content-Type: application/json;charset=utf-8");
$var_value_email = $_GET['email'];
$passwrd         = $_GET['pass'];
$user_name       = "id1015576_placeitnowapp";
$password        = "place311311place";
$database        = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$sql             = "SELECT * FROM `Visitors` WHERE `Email`='$var_value_email' And   `Password`='$passwrd'";
$result          = $conn->query($sql);

if ($result->num_rows > 0) {

    $myObj->status = "true";
	   while($row = $result->fetch_assoc()) {
	$myObj->name = $row["Name"];
	$myObj->phone = $row["Phone"];
	$myObj->email = $row["Email"];
	$myObj->pass = $row["Password"];
	$myObj->ID = $row["ID"];
	   }
 
} else {
    
    $myObj->status = "false";
}
   $myJSON = json_encode($myObj);
    
    echo $myJSON;

?>