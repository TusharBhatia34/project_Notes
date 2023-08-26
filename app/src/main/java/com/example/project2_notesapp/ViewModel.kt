package com.example.project2_notesapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ViewModel(
    private val dao: Dao
): ViewModel() {

    //will be triggered when we change it's value and we did it in every function inside when expression except "Event.DeleteName"
    private val _state = MutableStateFlow(State())


    //trigger when _state emit new value
    // return list of all the objects of our table in flow with the help of flatMaplatest and convert to stateflow with the help of StateIn
private val _listOfTable = _state.flatMapLatest {
   dao.getAllItems()  // contains all the list of our table's content
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


//using it to assign that list to state.list_name so it can display on our screen
//get trigger when we _state or _listoftable emits value or when state value gets changed
val state = combine(_state,_listOfTable){_state ,list_of_table->
    _state.copy(
        list_name = list_of_table //assigning
    )
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), State())

    fun onEvent(event: Event){

        when(event){
          is  Event.AddName -> toAdd()
          is  Event.DeleteName -> toDelete(event.table)
            Event.HideDialog -> toHideDialog()
            Event.ShowDialog -> toShowDialog()
            is Event.SetName -> toSetName(event.name)
           is Event.GetDeleteTopbar -> toGetDeleteTopbar(event.title)
            Event.GetAddTopbar -> toGetAddTopbar()
            is Event.SetContent -> toSetContent(event.title,event.content,event.id)
            is Event.updateContent->toUpdateContent(event.id,event.title,event.content)

        }
    }

    private fun toUpdateContent(id: Int, title: String, content: String) {
      val table =  Table(
          id = id,
          name = title,
          content = content
      )
        _state.update { it.copy(
           title = title,
            content = content
        )
        }
viewModelScope.launch {
    dao.addName(table)
}
    }

    private fun toGetDeleteTopbar(title:String) {
        _state.update { it.copy(
           isDeletingName = true
        , title = title
       )
        }
    }
    private fun toGetAddTopbar() {
        _state.update { it.copy(
           isDeletingName = false,
            name = ""
       )
        }
    }


    private fun toSetName(name: String) {
_state.update { it.copy(
            name = name
)
}
    }

    private fun toSetContent(title : String,content: String,id:Int) {
_state.update { it.copy(
         title = title,
    content = content,
    id = id

)
}
    }


    private fun toDelete(table: Table){

        viewModelScope.launch {
            dao.deleteName(table)
        }
        _state.update { it.copy(
            isDeletingName = false


        )
        }
    }

    private fun toAdd (){
        val name = state.value.name

        if (name.isBlank()){
            return
        }

     val table = Table(
            name = name,
         content = ""
        )

        viewModelScope.launch {
            dao.addName(table)

        }
        _state.update {it.copy(
            title = name,
            name = "",
            isAddingName = false
        )

        }

    }

      private fun toHideDialog() {

_state.update {it.copy(
    isAddingName = false,
    name = ""
)

}
    }

    private fun toShowDialog() {
        _state.update {it.copy(
            isAddingName = true
        )
        }
}



}




