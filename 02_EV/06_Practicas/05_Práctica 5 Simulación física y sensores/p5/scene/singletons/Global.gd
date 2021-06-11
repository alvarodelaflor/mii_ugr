extends Node

var number_items = 0


func instantiate_node(packed_scene, pos = null, parent = null):
	var clone = packed_scene.instance()
	
	var root = get_tree().root
	if parent == null:
		parent = root.get_child(root.get_child_count()-1)
	
	parent.add_child(clone)
	
	if pos != null:
		clone.global_transform.origin = pos
	
	return clone

func get_number_items():
	return number_items
	
func add_one_item():
	if number_items < 2:
		number_items += 1
	
func delete_one_item():
	if number_items > 0:
		number_items -=1
