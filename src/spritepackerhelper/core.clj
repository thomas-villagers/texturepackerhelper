(ns spritepackerhelper.core
  (:gen-class)
  (:import (javax.swing JFrame JLabel)
           (java.awt BorderLayout Dimension)
           (java.awt.dnd DropTarget)
           (filedrop FileDrop FileDrop$Listener )
           (com.badlogic.gdx.tools.imagepacker TexturePacker2)))

(def label-text "Drag the folder with sprites to pack here" )

(defn App []
  (let [frame (JFrame. "Sprite Packer")
        label (JLabel. label-text JLabel/CENTER)
        filedrop (FileDrop. label
                            (proxy [FileDrop$Listener] []
                              (filesDropped [files]
                                (doseq [f files]
                                  (when (.isDirectory f)
                                    (future 
                                      (.setText label (str "Generate " (.getPath f) "-packed"))
                                      (.repaint label)
                                      (TexturePacker2/main (into-array ^String [(.getPath f)]))
                                      (Thread/sleep 1000)
                                      (.setText label label-text)
                                      (.repaint label)))))))]
    (.setPreferredSize label (Dimension. 500 500))
    (.add (.getContentPane frame) label BorderLayout/CENTER)
    (doto frame
      (.pack)
      (.setSize 500 500)
      (.setVisible true))))

(defn -main []
  (App))
