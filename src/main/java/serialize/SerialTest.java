package serialize;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SerialTest implements Serializable {

    private String name;

    private Double num;

    private Map<String,Object> param;

    private List<String> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerialTest test = (SerialTest) o;

        if (name != null ? !name.equals(test.name) : test.name != null) return false;
        if (num != null ? !num.equals(test.num) : test.num != null) return false;
        if (param != null ? !param.equals(test.param) : test.param != null) return false;
        return list != null ? list.equals(test.list) : test.list == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (param != null ? param.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }
}
