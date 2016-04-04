package logic.gameComponents;

import logic.dynamicValues.Dynamic;
import logic.dynamicValues.DynamicParameter;
import logic.dynamicValues.DynamicParameterMap;
import logic.gameComponents.generator.GeneratorId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * GComponentAbstract реализует основной функционал для GComponent.
 */
public abstract class GComponentAbstract<GPanel> implements GComponent<GPanel>, Dynamic {
    private static final Logger logger = LogManager.getLogger(GComponentAbstract.class);
    /**
     * Панель которая будет отображать игровой компонент.
     */
    private GPanel gPanel;

    /**
     * Id уникальный код для всех GComponentAbstract
     */
    private int id;

    /**
     * Хранит в себе динамические параметры и может добавлять новые.
     */
    private DynamicParameterMap mapParameter;

    /**
     * Создание GComponentAbstract
     */
    public GComponentAbstract() {
        generateId();
        logger.info("Create " + this.toString());
    }

    /**
     * Создание GComponentAbstract с переменными параметрами.
     *
     * @param gPanel которая будет отображать данный GComponentAbstract.
     */
    public GComponentAbstract(GPanel gPanel) {
        generateId();
        this.mapParameter = new DynamicParameterMap();
        this.gPanel = gPanel;
        logger.info("Create " + this.toString());
    }

    @Override
    public DynamicParameter<String, String> getDynamicValues() {
        return mapParameter;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public GPanel getGPanel() {
        logger.debug("getGPanel " + gPanel);
        return gPanel;
    }

    @Override
    public void setGPanel(GPanel gPanel) {
        logger.debug("setGPanel " + gPanel);
        this.gPanel = gPanel;
    }

    private void generateId() {
        id = GeneratorId.generateId();
        logger.debug("generateId =" + id);
    }

    @Override
    public boolean equals(Object o) {
        return getClass() == o.getClass() && (this.id == o.hashCode());
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "GComponentAbstract{" +
                "id=" + id +
                ", gPanel=" + gPanel +
                '}';
    }
}