package domaci3.raf.domaci3.services;

public interface IService<T, ID> {
    <S extends T> S save(S var1);

}
