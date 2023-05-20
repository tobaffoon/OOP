package ru.nsu.amazyar.entities;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.amazyar.bases.Direction;

public class EntityGroup implements Entity {
    private SimpleEntity mainEntity;
    private final List<SimpleEntity> children;

    public EntityGroup(SimpleEntity mainEntity) {
        this.mainEntity = mainEntity;
        this.children = new ArrayList<>();
        children.add(mainEntity);
    }

    public void setMainEntity(SimpleEntity mainSimpleEntity){
        this.mainEntity = mainSimpleEntity;
    }

    public SimpleEntity getMainEntity(){
        return mainEntity;
    }

    public List<SimpleEntity> getChildren(){
        return children;
    }

    public void addChild(SimpleEntity entity){
        children.add(entity);
    }

    @Override
    public int getX() {
        return mainEntity.getX();
    }

    @Override
    public void setX(int x) {
        mainEntity.setX(x);
    }

    @Override
    public int getY() {
        return mainEntity.getY();
    }

    @Override
    public void setY(int y) {
        mainEntity.setY(y);
    }

    @Override
    public Direction getCurrentDirection() {
        return mainEntity.getCurrentDirection();
    }

    @Override
    public void move() {
        children.forEach(SimpleEntity::move);
    }
}
