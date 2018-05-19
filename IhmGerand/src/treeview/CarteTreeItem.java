package treeview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import model.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CarteTreeItem extends TreeItem<IElement> {
    public CarteTreeItem(IElement value) {
        super(value);
        super.getChildren().setAll(buildChildren(this));
        setExpanded(true);
    }

    public CarteTreeItem(IElement value, CarteTreeItem cti) {
        super(value);
        super.getChildren().setAll(buildChildren(this, cti));
        setExpanded(true);
    }


    private ObservableList<TreeItem<IElement>> buildChildren(CarteTreeItem item) {
        if(item.getValue() == null || !(item.getValue() instanceof ICompoPlat)) return FXCollections.observableArrayList();
        ICompoPlat c = (ICompoPlat)item.getValue();
        ObservableList<TreeItem<IElement>> elements = FXCollections.observableArrayList();
        c.getCompo().forEach((key, value) -> {
            TreeItem<IElement> cat = new CarteTreeItem(key);
            for (Plat p :
                    value) {
                cat.getChildren().add(new CarteTreeItem(p));
            }
            PlusElement p = new PlusElement();
            p.setName(Plat.class.getSimpleName());
            cat.getChildren().add(new CarteTreeItem(p));
            elements.add(cat);
        });
        PlusElement p = new PlusElement();
        p.setName(Categorie.class.getSimpleName());
        elements.add(new CarteTreeItem(p));

        //ajout formule
        if(item.getValue() instanceof Carte){
            CarteTreeItem cti = new CarteTreeItem(new PlusElement("Formules"));
            Carte carte = (Carte) item.getValue();
            carte.getFormules().forEach(formule -> {
                CarteTreeItem f = new CarteTreeItem(formule);
                cti.getChildren().add(f);
            });
            cti.getChildren().add(new CarteTreeItem(new PlusElement(Formule.class.getSimpleName())));

            elements.add(cti);
        }
        return elements;
    }

    private boolean isExpand(CarteTreeItem ancient, IElement e){
        for(TreeItem treeItem: ancient.getChildren()){
            if(e.equals(treeItem.getValue())){
                return treeItem.isExpanded();
            }
        }
        return true;
    }

    private TreeItem getTreeItem(CarteTreeItem ancient, IElement e){
        for(TreeItem treeItem: ancient.getChildren()){
            if(e.equals(treeItem.getValue())){
                return treeItem;
            }
        }
        return null;
    }

    private ObservableList<TreeItem<IElement>> buildChildren(CarteTreeItem item, CarteTreeItem ancient) {
        if(item.getValue() == null || !(item.getValue() instanceof ICompoPlat)) return FXCollections.observableArrayList();
        ICompoPlat c = (ICompoPlat)item.getValue();
        ObservableList<TreeItem<IElement>> elements = FXCollections.observableArrayList();
        for(Map.Entry<Categorie, Set<Plat>> e: c.getCompo().entrySet()){
            TreeItem<IElement> cat = new CarteTreeItem(e.getKey());
            cat.setExpanded(isExpand(ancient, e.getKey()));
            for (Plat p :
                    e.getValue()) {
                cat.getChildren().add(new CarteTreeItem(p));
            }
            PlusElement p = new PlusElement();
            p.setName(Plat.class.getSimpleName());
            cat.getChildren().add(new CarteTreeItem(p));
            elements.add(cat);
        }
        PlusElement p = new PlusElement();
        p.setName(Categorie.class.getSimpleName());
        elements.add(new CarteTreeItem(p));

        //ajout formule
        if(item.getValue() instanceof Carte){
            CarteTreeItem cti = new CarteTreeItem(new PlusElement("Formules"));
            TreeItem ati = getTreeItem(ancient, cti.getValue());
            if(ati != null) cti.setExpanded(ati.isExpanded());
            Carte carte = (Carte) item.getValue();
            carte.getFormules().forEach(formule -> {
                TreeItem ftia = getTreeItem((CarteTreeItem)ati, formule);
                CarteTreeItem f;
                if(ftia != null) {
                    f = new CarteTreeItem(formule, (CarteTreeItem) ftia);
                    f.setExpanded(ftia.isExpanded());
                }else{
                    f = new CarteTreeItem(formule);
                }
                cti.getChildren().add(f);
            });
            cti.getChildren().add(new CarteTreeItem(new PlusElement(Formule.class.getSimpleName())));

            elements.add(cti);
        }
        return elements;
    }

}
