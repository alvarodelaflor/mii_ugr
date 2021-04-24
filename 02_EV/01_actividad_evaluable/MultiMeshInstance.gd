tool
extends MultiMeshInstance

export var cube_per_side = 16
	
func setup_mutimesh():
	self.multimesh = MultiMesh.new()
	self.multimesh.transform_format = MultiMesh.TRANSFORM_3D
	
	self.multimesh.instance_count = cube_per_side * cube_per_side * cube_per_side
	self.multimesh.visible_instance_count = self.multimesh.instance_count
	
	var mesh = CubeMesh.new()
	mesh.size = Vector3(1,1,1)
	self.multimesh.mesh = mesh
	
	var cube_size = 1
	var cube_half_size = cube_size / 2.0
	var origin_distance = cube_per_side * cube_half_size - cube_half_size
	var origin = -Vector3(origin_distance, origin_distance, origin_distance)
	var index = 0
	
	var pos = origin * Vector3(0, 0, 0)
	self.multimesh.set_instance_transform(index, Transform(Basis(), pos))
	
func _ready():
	setup_mutimesh()
