package competition;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. október 02.
 * @modified 2020. október 17.
 */
public abstract class Being {

    protected String name;
    protected int water;

    protected int maxWater;
    protected boolean isLive;
    protected int distance;

    public Being(String name, int water, int maxWater,
            boolean isLive, int distance) {
        this.name = name;
        this.water = water;
        this.maxWater = maxWater;
        this.isLive = isLive;
        this.distance = distance;
    }

    abstract void move(int x);

    abstract void day(Weather weather);
}

class SandWalker extends Being {

    public SandWalker(String name, int water, int maxWater,
            boolean isLive, int distance) {
        super(name, water, maxWater, isLive, distance);
    }

    @Override
    public void move(int x) {
        this.distance += x;
    }

    @Override
    public void day(Weather weather) {
        weather.cross(this);
    }
}

class Sponge extends Being {

    public Sponge(String name, int water, int maxWater,
            boolean isLive, int distance) {
        super(name, water, maxWater, isLive, distance);
    }

    @Override
    public void move(int x) {
        this.distance += x;
    }

    @Override
    public void day(Weather weather) {
        weather.cross(this);
    }
}

class Walker extends Being {

    public Walker(String name, int water, int maxWater,
            boolean isLive, int distance) {
        super(name, water, maxWater, isLive, distance);
    }

    @Override
    public void move(int x) {
        this.distance += x;
    }

    @Override
    public void day(Weather weather) {
        weather.cross(this);
    }
}
