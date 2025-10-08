package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    lateinit var consultarAcervoRecyclerView: RecyclerView
    lateinit var reservasRecyclerView: RecyclerView
    lateinit var submeterProducaoRecyclerView: RecyclerView
    lateinit var meusEmprestimosRecyclerView: RecyclerView
    lateinit var exposicaoDosAlunosRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    private fun setupRecyclerViews(){
        //Consultar Acervoa
        consultarAcervoRecyclerView = findViewById(R.id.btn_search_acervos)
        consultarAcervoRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        //reservas
        reservasRecyclerView = findViewById(R.id.btn_reservations)
        reservasRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        //submeter producao
        submeterProducaoRecyclerView = findViewById(R.id.btn_submit_prod)
        submeterProducaoRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        //meus emprestimos
        meusEmprestimosRecyclerView = findViewById(R.id.btn_my_loans)
        meusEmprestimosRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        //exposicao dos alunos
        exposicaoDosAlunosRecyclerView = findViewById(R.id.btn_student_productions)
        exposicaoDosAlunosRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }
}