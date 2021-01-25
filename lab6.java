class Tbuffer
{
	private static int[] vec1;
	private int n;
	public Tbuffer(final int _n)
	{
		vec1 = new int[_n];
		this.n = _n;
	}

	public void fill()
	{
		for(int i = 0; i < this.n ; i++)
		{
			vec1[i] = i;
		}
	}

	public int get_size()
	{
		return this.n;
	}
	
	public int get(int i)
	{
		return vec1[i];
	}

	public void set(int i,int x)
	{
		vec1[i] = x;
	}
}

class T implements Runnable
{
	private Tbuffer[] bufs;
	private int b,id,n_threads;
	public T(Tbuffer[] _bufs,int B,int _id,int n_threads)
	{
		bufs = _bufs;
		b = B;
		id = _id;
		this.n_threads = n_threads;
		System.out.println("Thread " + id + " comecou");	
	}
	public void run()
	{
		System.out.println("Thread " + id + " executando, tamanho do array: " + bufs[0].get_size() );
		
		int size = bufs[0].get_size();
		for(int i = id; i < size; i += n_threads)
		{
			//System.out.println("Thread " + id + " no loop " + i);
			int soma = 0;
			for(int j = 0; j < b-1; j++)
				soma += bufs[j].get(i);
			bufs[b - 1].set(i,soma);
		}
		System.out.println("Thread " + id + " terminou");
	}
}



class Lab
{
	static final int N = 4,B = 3,v_size = 100;
	public static void main(String[] args)
	{
		Thread[] threads = new Thread[N];
		Tbuffer[] buffers = new Tbuffer[B];
		for(int i = 0; i < B; i++)
		{
			buffers[i] = new Tbuffer(v_size);
			buffers[i].fill();
		}
		for(int i = 0; i < N; i++)
			threads[i] = new Thread(new T(buffers,B,i,N));
		for(int i = 0; i < N; i++)
			threads[i].start();
		System.out.println("Espereando as Threads terminarem");
		for(int i = 0; i < N; i++)
		{
			try{threads[i].join();}
			catch(InterruptedException e){return;}
		}
		System.out.println("Imprimindo resultado:");
		for(int i = 0; i < v_size; i++)
			System.out.println(buffers[B - 1].get(i));
		System.out.println("FIM");
	}
}
