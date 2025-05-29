package Lr_6.UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

/**
 * 
 */
public class RadioButtonsGroup {

    List<JLabel> buttons;
    List<ActionListener> listeners;

    Object selectedID;

    Color ACTIVE_COLOR, INACTIVE_COLOR;

    /**
     * 
     */
    public RadioButtonsGroup() {
	buttons = new ArrayList<>();
	listeners = new ArrayList<>();

	selectedID = null;
    }

    /**
     * 
     * @param panel
     * @param text
     * @param sign
     * @param aCTIVE_COLOR
     * @param iNACTIVE_COLOR
     */
    public void addButton(Container panel, String text, Object sign, Color aCTIVE_COLOR, Color iNACTIVE_COLOR) {

	ACTIVE_COLOR = aCTIVE_COLOR;
	INACTIVE_COLOR = iNACTIVE_COLOR;

	JLabel label = new JLabel(text);
	label.setForeground(INACTIVE_COLOR);
	label.setOpaque(false);
	label.putClientProperty("sign", sign); // нет гарантии уникальности
	label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	label.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		setActive(label.getClientProperty("sign"));
	    }
	});
	buttons.add(label);
	panel.add(label);
    }

    /**
     * 
     * @return
     */
    public Object getActive() {
	return selectedID;
    }

    /**
     * 
     * @param selectedID
     */
    public void setActive(Object selectedID) {
	this.selectedID = selectedID; // Не безопасно
	update();
	invokeSelectedIdChangedEvent();
    }

    /**
     * 
     * @param listener
     */
    public void addListener_SelectedIdChangedEvent(ActionListener listener) {
	listeners.add(listener);
    }

    /**
     * 
     * @param listener
     */
    public void removeListener_SelectedIdChangedEvent(ActionListener listener) {
	listeners.remove(listener);
    }

    /**
     * 
     */
    private void invokeSelectedIdChangedEvent() {
	ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, selectedID.toString()); // ToString тупо
	for (ActionListener listener : listeners) {
	    listener.actionPerformed(event);
	}
    }

    /**
     * 
     */
    private void update() {
	for (JLabel label : buttons) {
	    if (label.getClientProperty("sign").equals(selectedID)) { // нет гарантии уникальности
		label.setForeground(ACTIVE_COLOR);
	    } else {
		label.setForeground(INACTIVE_COLOR);
	    }
	}
    }
}
