tool
extends MeshInstance

var verts = PoolVector3Array()
var normals = PoolVector3Array()
var indices = PoolIntArray()

export var cube_size = 2 setget reload_scene

func reload_scene(new_size_cube):
	cube_size = new_size_cube
	setup_mutimesh()

func _ready():
	setup_mutimesh()
	
func setup_mutimesh():
	self.mesh = CubeMesh.new()
	var arr_mesh = ArrayMesh.new()
	var mesh_arrays = []
	mesh_arrays.resize(ArrayMesh.ARRAY_MAX)
	make_cube(0, 0, 0)
	mesh_arrays[Mesh.ARRAY_VERTEX] = verts
	mesh_arrays[Mesh.ARRAY_NORMAL] = normals
	mesh_arrays[Mesh.ARRAY_INDEX] = indices
	arr_mesh.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, mesh_arrays)
	self.mesh = arr_mesh

func make_cube(x,y,z):
	#left
	make_quad(Vector3(x,y,z), Vector3(x,y+cube_size,z), Vector3(x,y+cube_size,z+cube_size), Vector3(x,y,z+cube_size))
	#right
	make_quad(Vector3(x+cube_size,y,z+cube_size), Vector3(x+cube_size,y+cube_size,z+cube_size), Vector3(x+cube_size,y+cube_size,z), Vector3(x+cube_size,y,z))
	#down
	make_quad(Vector3(x+cube_size,y,z), Vector3(x,y,z), Vector3(x,y,z+cube_size), Vector3(x+cube_size,y,z+cube_size))
	#up
	make_quad(Vector3(x+cube_size,y+cube_size,z+cube_size), Vector3(x,y+cube_size,z+cube_size), Vector3(x,y+cube_size,z), Vector3(x+cube_size,y+cube_size,z))
	#back
	make_quad(Vector3(x+cube_size,y,z), Vector3(x+cube_size,y+cube_size,z), Vector3(x,y+cube_size,z), Vector3(x,y,z))
	#front
	make_quad(Vector3(x,y,z+cube_size), Vector3(x,y+cube_size,z+cube_size), Vector3(x+cube_size,y+cube_size,z+cube_size), Vector3(x+cube_size,y,z+cube_size))

func make_quad(a,b,c,d):
	var length = len(verts)
	indices.append_array([length, length+1, length+2, length, length+2, length+3])
	verts.append_array([a,b,c,d])
	normals.append_array([a.normalized(),b.normalized(),c.normalized(),d.normalized()])
