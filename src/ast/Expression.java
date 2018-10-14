package ast;

import java.util.Map;

public interface Expression {
   Type static_type_check(Map<String, TypeScope> local_map);
}
