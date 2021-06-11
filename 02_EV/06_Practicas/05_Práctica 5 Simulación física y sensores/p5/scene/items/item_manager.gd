extends Spatial


# Almaceenamos todos los items que puede tener el jugador
var all_items = {}

# Almacenamos los items que porta el jugador
var items = {}

#HUD
var hud

# Item actual
var current_item # Reference to the current item equipped
var current_item_slot = "Empty" # The current item slot

var changing_item = false
var unequipped_item = false

var item_index = 0 # For switching items through mouse wheel


func _ready():
	
	hud = owner.get_node("HUD")
	get_parent().get_node("Camera/RayCast").add_exception(owner) # Adds exception of player to the shooting raycast
	
	all_items = {
		"Nothing" : preload("res://scene/items/nothing/nothing.tscn"),
		"Zapatilla" : preload("res://scene/items/something/deporte/deporte_a/zapatilla.tscn"),
		"Botella" : preload("res://scene/items/something/agua/agua_a/botella.tscn")
	}
	
	items = {
		"Empty" : $Nothing,
		"Primary" : $Empty,
		"Secondary" : $Empty
	}
	
	# Initialize references for each items
	for w in items:
		if is_instance_valid(items[w]):
			item_setup(items[w])
	
	# Set current item to nothing
	current_item = items["Empty"]
	change_item("Empty")
	
	# Disable process
	set_process(false)


# Initializes item's values
func item_setup(i):
	i.item_manager = self
	i.player = owner
	i.ray = get_parent().get_node("Camera/RayCast")
	i.visible = false


# Process will be called when changing items
func _process(delta):
	
	if unequipped_item == false:
		if current_item.is_unequip_finished() == false:
			return
		
		unequipped_item = true
		
		current_item = items[current_item_slot]
		current_item.equip()
	
	if current_item.is_equip_finished() == false:
		return
	
	changing_item = false
	set_process(false)



func change_item(new_item_slot):
	
	if new_item_slot == current_item_slot:
		current_item.update_item() # Refresh
		return
	
	if is_instance_valid(items[new_item_slot]) == false:
		return
	
	current_item_slot = new_item_slot
	changing_item = true
	
	items[current_item_slot].update_item() # Updates the item data on UI, as soon as we change a item
	update_item_index()
	
	# Change items
	if is_instance_valid(current_item):
		unequipped_item = false
		current_item.unequip()
	
	set_process(true)




# Scroll item change
func update_item_index():
	match current_item_slot:
		"Empty":
			item_index = 0
		"Primary":
			item_index = 1
		"Secondary":
			item_index = 2

func next_item():
	item_index += 1
	
	if item_index >= items.size():
		item_index = 0
	
	change_item(items.keys()[item_index])

func previous_item():
	item_index -= 1
	
	if item_index < 0:
		item_index = items.size() - 1
	
	change_item(items.keys()[item_index])

# Wear Pickup
func add_wear(amount):
	if is_instance_valid(current_item) == false || current_item.name == "Nothing":
		return false
	
	current_item.update_item("add", amount)
	return true



# Add item to an existing empty slot
func add_item(item_data):
	
	if not item_data["Name"] in all_items:
		return
	
	if is_instance_valid(items["Primary"]) == false:
		
		# Instance the new item
		var item = Global.instantiate_node(all_items[item_data["Name"]], Vector3.ZERO, self)
		
		# Initialize the new item references
		item_setup(item)
		item.wear_in_item = item_data["Wear"]
		item.maximum_wear = item_data["MaximumWear"]
		item.mag_size = item_data["WearSize"]
		item.transform.origin = item.equip_pos
		
		
		items["Primary"] = item
		change_item("Primary")
		
		return
	
	if is_instance_valid(items["Secondary"]) == false:
		
		var item = Global.instantiate_node(all_items[item_data["Name"]], Vector3.ZERO, self)
		
		item_setup(item)
		item.wear_in_item = item_data["Wear"]
		item.maximum_wear = item_data["MaximumWear"]
		item.mag_size = item_data["WearSize"]
		item.transform.origin = item.equip_pos
		
		items["Secondary"] = item
		change_item("Secondary")
		
		return

func drop_item():
	if current_item_slot != "Empty":
		current_item.drop_item()
		
		current_item = "Empty"
		current_item = items["Empty"]
		
		current_item.update_item()
		
		change_item("Empty")

func switch_item(item_data):
	
	for i in items:
		if is_instance_valid(items[i]) == false:
			add_item(item_data)
			return
	
	if current_item.name == "Nothing":
		items["Primary"].drop_item()
		yield(get_tree(), "idle_frame")
		add_item(item_data)
	
	elif current_item.item_name == item_data["Name"]:
		add_wear(item_data["Wear"] + item_data["MaximumWear"])
	
	else:
		drop_item()
		
		yield(get_tree(), "idle_frame")
		add_item(item_data)


func show_interaction_prompt(item_name):
	var desc = "Add Wear" if current_item.item_name == item_name else "Coger"
	hud.show_interaction_prompt(desc)

func hide_interaction_prompt():
	hud.hide_interaction_prompt()

func process_item_pickup():
	var from = global_transform.origin
	var to = global_transform.origin - global_transform.basis.z.normalized() * 2.0
	var space_state = get_world().direct_space_state
	var collision = space_state.intersect_ray(from, to, [owner], 1)
	
	if collision:
		var body = collision["collider"]
		
		if body.has_method("get_item_pickup_data"):
			var item_data = body.get_item_pickup_data()
			
			show_interaction_prompt(item_data["Name"])
			
			if Input.is_action_just_pressed("interact"):
				switch_item(item_data)
				body.queue_free()
		else:
			hide_interaction_prompt()

func update_hud(item_data):
	var item_slot = "1"
	
	match current_item_slot:
		"Empty":
			item_slot = "1"
		"Primary":
			item_slot = "2"
		"Secondary":
			item_slot = "3"
	
	hud.update_item_ui(item_data, item_slot)
