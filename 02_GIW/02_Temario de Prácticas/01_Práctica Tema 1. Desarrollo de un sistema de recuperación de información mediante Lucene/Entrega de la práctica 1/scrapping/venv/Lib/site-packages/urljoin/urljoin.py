def url_path_join(*args, **kwargs):
    if "trailing_slash" not in kwargs.keys():
        kwargs["trailing_slash"] = False
    trailing_slash = kwargs["trailing_slash"]
    url = '/'.join(map(lambda x: x.strip().strip('/'), args))
    if trailing_slash:
        return url + "/"
    return url
