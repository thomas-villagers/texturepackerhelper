(ns spritepackerhelper.core
  (:gen-class)
  (:import (javax.swing JFrame JLabel)
           (java.awt BorderLayout Dimension)
           (java.awt.dnd DropTarget)
           (filedrop FileDrop FileDrop$Listener )
           (com.badlogic.gdx.tools.imagepacker TexturePacker2)))

(defn App []
  (let [frame (JFrame. "Sprite Packer")
        label (JLabel. "Drag the folder with sprites to pack here" JLabel/CENTER)
        filedrop (FileDrop. label
                            (proxy [FileDrop$Listener] []
                              (filesDropped [files]
                                (doseq [f files]
                                  (when (.isDirectory f)
                                    (println "Processing directory " (.getName f) (.getPath f))
                                    (TexturePacker2/main (into-array ^String [(.getPath f)])))))))]
    (.setPreferredSize label (Dimension. 500 500))
    (.add (.getContentPane frame) label BorderLayout/CENTER)
    (doto frame
      (.pack)
      (.setSize 500 500)
      (.setVisible true))))

(defn -main []
  (App))
