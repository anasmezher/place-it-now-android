<?php
header("Content-Type: application/json;charset=utf-8");
$PID       = $_GET['ID'];
$user_name = "id1015576_placeitnowapp";
$password  = "place311311place";
$database  = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$sql       = "SELECT * FROM `Comments` where  PlaceID =\"".$PID."\"";
$result    = $conn->query($sql);
$rows = array();

$i=0;
while ($row = mysqli_fetch_array($result)) {

	$rows[$i]['datee'] = $row['Date'];
	$rows[$i]['comment'] = $row['Comment'];
	$rows[$i]['pname'] = $row['PersonName'];
      $i++; 
}
 	  header('Content-type: application/json'); // display result JSON format
echo json_encode(array(
     'status' => true,
     'names' => $rows // this is your data variable
));

?>