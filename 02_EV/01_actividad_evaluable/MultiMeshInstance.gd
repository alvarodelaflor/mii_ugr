tool
extends MultiMeshInstance

export var cube_size = 2 setget reload_scene

func reload_scene(new_size_cube):
	cube_size = new_size_cube
	setup_mutimesh()
	
func setup_mutimesh():
	self.multimesh = MultiMesh.new()
	self.multimesh.transform_format = MultiMesh.TRANSFORM_3D
	
	self.multimesh.instance_count = cube_size * cube_size * cube_size
	self.multimesh.visible_instance_count = self.multimesh.instance_count
	
	var mesh = CubeMesh.new()
	mesh.size = Vector3(cube_size,cube_size, cube_size)
	self.multimesh.mesh = mesh
	
	var cube_size = 1
	var cube_half_size = cube_size / 2.0
	var origin_distance = cube_size * cube_half_size - cube_half_size
	var origin = -Vector3(origin_distance, origin_distance, origin_distance)
	var index = 0
	
	var pos = origin * Vector3(0, 0, 0)
	self.multimesh.set_instance_transform(index, Transform(Basis(), pos))
	
func _ready():
	setup_mutimesh()
