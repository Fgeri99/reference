package competition;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. október 02.
 * @modified 2020. október 17.
 */
public abstract class Weather {

    abstract void cross(SandWalker sw);
    abstract void cross(Sponge sp);
    abstract void cross(Walker wk);
}

class Rainy extends Weather {

    @Override
    public void cross(SandWalker sw) {
        sw.water += 3;
        if (sw.water > sw.maxWater) {
            sw.water = sw.maxWater;
        }
        if (sw.water <= 0) {
            sw.isLive = false;
            sw.water = 0;
        } else {
            sw.move(0);
        }
    }

    @Override
    public void cross(Sponge sp) {
        sp.water += 6;
        if (sp.water > sp.maxWater) {
            sp.water = sp.maxWater;
        }
        if (sp.water <= 0) {
            sp.isLive = false;
            sp.water = 0;
        } else {
            sp.move(3);
        }
    }

    @Override
    public void cross(Walker wk) {
        wk.water += 3;
        if (wk.water > wk.maxWater) {
            wk.water = wk.maxWater;
        }
        if (wk.water <= 0) {
            wk.isLive = false;
            wk.water = 0;
        } else {
            wk.move(1);
        }
    }
}

class Cloudy extends Weather {

    @Override
    public void cross(SandWalker sw) {
        sw.water += 0;
        if (sw.water > sw.maxWater) {
            sw.water = sw.maxWater;
        }
        if (sw.water <= 0) {
            sw.isLive = false;
            sw.water = 0;
        } else {
            sw.move(1);
        }
    }

    @Override
    public void cross(Sponge sp) {
        sp.water += (-1);
        if (sp.water > sp.maxWater) {
            sp.water = sp.maxWater;
        }
        if (sp.water <= 0) {
            sp.isLive = false;
            sp.water = 0;
        } else {
            sp.move(1);
        }
    }

    @Override
    public void cross(Walker wk) {
        wk.water += (-1);
        if (wk.water > wk.maxWater) {
            wk.water = wk.maxWater;
        }
        if (wk.water <= 0) {
            wk.isLive = false;
            wk.water = 0;
        } else {
            wk.move(2);
        }
    }
}

class Sunny extends Weather {

    @Override
    public void cross(SandWalker sw) {
        sw.water += (-1);
        if (sw.water > sw.maxWater) {
            sw.water = sw.maxWater;
        }
        if (sw.water <= 0) {
            sw.isLive = false;
            sw.water = 0;
        } else {
            sw.move(3);
        }
    }

    @Override
    public void cross(Sponge sp) {
        sp.water += (-4);
        if (sp.water > sp.maxWater) {
            sp.water = sp.maxWater;
        }
        if (sp.water <= 0) {
            sp.isLive = false;
            sp.water = 0;
        } else {
            sp.move(0);
        }
    }

    @Override
    public void cross(Walker wk) {
        wk.water += (-2);
        if (wk.water > wk.maxWater) {
            wk.water = wk.maxWater;
        }
        if (wk.water <= 0) {
            wk.isLive = false;
            wk.water = 0;
        } else {
            wk.move(1);
        }
    }
}
