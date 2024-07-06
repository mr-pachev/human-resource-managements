package bg.softuni.human_resource_managements.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException {
    private final Object id;

    public ObjectNotFoundException(String message, Object id) {
        super();
        this.id = id;
    }


    public Object getId() {
        return id;
    }
}
