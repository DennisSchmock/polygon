<form action="room" method="POST">
    <div class="w3-row-padding">
        <input type="hidden" name="action" value="addroomsubmit" />
        <input type="hidden" name="floorID" value="${requestScope.floorId}" />
        <div class="w3-half"><label>Room Name:</label><input type="text" name="roomname" value="" required class="w3-input w3-border"/></div><br>
        <div class="w3-half"><input name="addRoom" type="submit" value="Add Room" /></div>

    </div>
</form>    

