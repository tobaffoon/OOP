package ru.nsu.amazyar.entities;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.amazyar.bases.Direction;

public class MovableEntityLinkedList implements MovableEntity {
    private SimpleMovableEntity mainEntity;
    private final List<SimpleMovableEntity> children;

    public MovableEntityLinkedList(SimpleMovableEntity mainEntity) {
        this.mainEntity = mainEntity;
        this.children = new ArrayList<>();
        children.add(mainEntity);
    }

    public void setMainEntity(SimpleMovableEntity mainSimpleMovableEntity){
        this.mainEntity = mainSimpleMovableEntity;
    }

    public SimpleMovableEntity getMainEntity(){
        return mainEntity;
    }

    public List<SimpleMovableEntity> getChildren(){
        return children;
    }

    public void addChild(SimpleMovableEntity entity){
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

    public void changeDirection(Direction direction){
        mainEntity.changeDirection(direction);
    }

    @Override
    public Direction getCurrentDirection() {
        return mainEntity.getCurrentDirection();
    }

    @Override
    public void move() {
        children.forEach(SimpleMovableEntity::move);
    }
}
