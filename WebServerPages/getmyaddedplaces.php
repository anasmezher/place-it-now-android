<?php
header("Content-Type: application/json;charset=utf-8");
$ID        = $_GET['ID'];
$user_name = "id1015576_placeitnowapp";
$password  = "place311311place";
$database  = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$conn2 = new mysqli($server , $user_name , $password,$database );
$conn3 = new mysqli($server , $user_name , $password,$database );
$sql       = "SELECT * FROM `places` where VisitorID=\"".$ID."\"";
$result    = $conn->query($sql);

$rows = array();

$i=0;
while ($row = mysqli_fetch_array($result)) {
         $rows[$i]['ID'] = $row['ID']; // rest similarly 
		 $rows[$i]['pname'] = $row['PlaceName']; // rest similarly 
		 $rows[$i]['ptype'] = $row['Type']; // rest similarly 
		 $rows[$i]['imagename'] =  $row['imagename']; // rest similarly 

    $i++; 
}
 	  header('Content-type: application/json'); // display result JSON format
echo json_encode(array(
     'status' => true,
     'names' => $rows // this is your data variable
));

?>