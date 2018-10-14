package ast;

import java.util.Map;

public interface Lvalue {
   Type static_type_check(Map<String, TypeScope> local_map);
}
